package bob.colbaskin.webantpractice.common.photo_api.data

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.utils.toImageBitmap
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosRepository
import bob.colbaskin.webantpractice.home.domain.models.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class PhotoPagingSource(
    private val context: Context,
    private val photosRepository: PhotosRepository,
    private val new: Boolean?,
    private val popular: Boolean?,
    private val itemsPerPage: Int
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        return try {
            val response = photosRepository.getPhotos(
                page = page,
                itemsPerPage = itemsPerPage,
                order = "desc",
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

                    val imageState = imageResult.toImageBitmap(context)
                    val nameState = processNameResult(nameResult)

                    photo.copy(
                        imageState = imageState,
                        name = nameState
                    )
                }
            }.awaitAll()
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