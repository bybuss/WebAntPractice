package bob.colbaskin.webantpractice.home.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import bob.colbaskin.webantpractice.home.data.local.room.AppDatabase
import bob.colbaskin.webantpractice.home.data.local.room.PhotoEntity
import bob.colbaskin.webantpractice.home.domain.PhotosApiService

private const val TAG = "Photos"

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator(
    private val db: AppDatabase,
    private val photosApi: PhotosApiService,
    private val isNew: Boolean? = null,
    private val isPopular: Boolean? = null
): RemoteMediator<Int, PhotoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    (lastItem?.id?.div(state.config.pageSize)?.plus(1)) ?: 1
                }
            }

            val response = photosApi.getPhotos(
                page = page,
                itemsPerPage = state.config.pageSize,
                order = null,
                new = isNew,
                popular = isPopular
            )

            val entities = response.hydraMember.map { photo ->
                PhotoEntity(
                    id = photo.id,
                    fileId = photo.file.id,
                    filePath = photo.file.path,
                    new = photo.new,
                    popular = photo.popular
                )
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.photoDao().clearByType(isNew, isPopular)
                }
                db.photoDao().insertPhotos(entities)
            }

            MediatorResult.Success(endOfPaginationReached = entities.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "Error in RemoteMediator", e)
            MediatorResult.Error(e)
        }
    }
}
