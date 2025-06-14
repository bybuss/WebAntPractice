package bob.colbaskin.webantpractice.home.data

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.utils.safeApiCall
import bob.colbaskin.webantpractice.home.data.local.room.AppDatabase
import bob.colbaskin.webantpractice.home.data.local.room.PhotoEntity
import bob.colbaskin.webantpractice.home.domain.PhotosApiService
import bob.colbaskin.webantpractice.home.domain.PhotosRepository
import bob.colbaskin.webantpractice.home.domain.models.Photo
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

private const val TAG = "Photos"
private const val PAGE_SIZE = 20

class PhotosRepositoryImpl @Inject constructor(
    private val context: Context,
    private val photosApi: PhotosApiService,
    private val imageStorage: ImageStorage,
    private val db: AppDatabase
) : PhotosRepository {

    private val imageCache = mutableMapOf<String, ImageBitmap>()

    @OptIn(ExperimentalPagingApi::class)
    override fun getPhotosStream(
        new: Boolean?,
        popular: Boolean?
    ): Result<Flow<PagingData<Photo>>> {
        return try {
            val flow = Pager(
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    enablePlaceholders = false
                ),
                remoteMediator = PhotosRemoteMediator(
                    db = db,
                    photosApi = photosApi,
                    isNew = new,
                    isPopular = popular
                ),
                pagingSourceFactory = {
                    db.photoDao().getPhotos(isNew = new, isPopular = popular)
                }
            ).flow.map { pagingData ->
                pagingData.map { entity: PhotoEntity ->
                    entity.toDomain()
                }
            }
            Result.Success(flow)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting photos stream", e)
            Result.Error(
                title = context.getString(R.string.error_title),
                text = context.getString(R.string.error_text)
            )
        }
    }

    override suspend fun loadImage(photo: Photo): Result<ImageBitmap> {
        imageCache[photo.file.path]?.let {
            return Result.Success(it)
        }

        val entity = db.photoDao().getByPath(photo.file.path) ?: return Result.Error(
            title = context.getString(R.string.error_title),
            text = context.getString(R.string.error_text)
        )

        if (entity.localImagePath != null) {
            imageStorage.getImage(entity.localImagePath)?.let { bytes ->
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()?.let {
                    imageCache[photo.file.path] = it
                    return Result.Success(it)
                }
            }
        }

        return safeApiCall(
            apiCall = { photosApi.getFile(photo.file.path) },
            successHandler = { responseBody ->
                val bytes = responseBody.byteStream().readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    ?: throw IOException("Failed to decode image")
                val imageBitmap = bitmap.asImageBitmap()

                imageCache[photo.file.path] = imageBitmap

                val localPath = imageStorage.saveImage("img_${photo.id}", bytes)

                db.photoDao().updatePhoto(entity.copy(
                    localImagePath = localPath,
                    imageState = 2 // Mark as loaded
                ))

                imageBitmap
            },
            context = context
        )
    }
}
