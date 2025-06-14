package bob.colbaskin.webantpractice.home.data

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.paging.PagingSource
import androidx.paging.PagingState
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.home.domain.PhotosRepository
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

private const val ITEMS_PER_PAGE = 20

class PhotoPagingSource(
    private val context: Context,
    private val photosRepository: PhotosRepository,
    private val new: Boolean?,
    private val popular: Boolean?
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        return try {
            val response = photosRepository.getPhotos(
                page = page,
                itemsPerPage = ITEMS_PER_PAGE,
                order = null,
                new = new,
                popular = popular
            )

            when (response) {
                is Result.Success -> {
                    val photos = response.data

                    val photosWithImages = loadImagesAndNamesForPhotos(photos)
                    Log.d("PhotoPagingSource", "Photos loaded: $photosWithImages")

                    LoadResult.Page(
                        data = photosWithImages,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (photos.isEmpty()) null else page + 1
                    )
                }
                is Result.Error -> {
                    LoadResult.Error(Exception(response.text))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun loadImagesAndNamesForPhotos(photos: List<Photo>): List<Photo> {
        return withContext(Dispatchers.IO) {
            photos.map { photo ->
                async {
                    val imageDeferred = async { photosRepository.getFile(photo.file.path) }
                    val nameDeferred = async { photosRepository.getPhotoNameById(photo.id) }

                    val imageResult = imageDeferred.await()
                    val nameResult = nameDeferred.await()

                    val imageState = processImageResult(imageResult)
                    val nameState = processNameResult(nameResult)

                    photo.copy(
                        imageState = imageState,
                        name = nameState
                    )
                }
            }.awaitAll()
        }
    }

    private fun processImageResult(result: Result<ByteArray>): UiState<ImageBitmap> {
        return when (result) {
            is Result.Success -> {
                val bitmap = BitmapFactory.decodeByteArray(result.data, 0, result.data.size)
                bitmap?.asImageBitmap()?.let { UiState.Success(it) }
                    ?: UiState.Error(
                        title = context.getString(R.string.error_title),
                        text = context.getString(R.string.error_text)
                    )
            }
            is Result.Error -> UiState.Error(result.title, result.text)
        }
    }

    private fun processNameResult(result: Result<String>): UiState<String> {
        return when (result) {
            is Result.Success -> UiState.Success(result.data)
            is Result.Error -> UiState.Error(result.title, result.text)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }
}