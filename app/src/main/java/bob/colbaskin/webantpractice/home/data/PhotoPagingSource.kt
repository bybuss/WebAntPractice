package bob.colbaskin.webantpractice.home.data

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.paging.PagingSource
import androidx.paging.PagingState
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.home.domain.PhotosRepository
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
                itemsPerPage = 20,
                order = null,
                new = new,
                popular = popular
            )

            when (response) {
                is Result.Success -> {
                    val photos = response.data

                    val photosWithImages = loadImagesForPhotos(photos)

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

    private suspend fun loadImagesForPhotos(photos: List<Photo>): List<Photo> {
        return withContext(Dispatchers.IO) {
            photos.map { photo ->
                val imageResult = photosRepository.getFile(photo.file.path)

                val newState = when (imageResult) {
                    is Result.Success -> {
                        val bitmap = BitmapFactory.decodeByteArray(
                            imageResult.data,
                            0,
                            imageResult.data.size
                        )
                        bitmap?.asImageBitmap()?.let { UiState.Success(it) }
                            ?: UiState.Error(
                                title = context.getString(R.string.error_title),
                                text = context.getString(R.string.error_text)
                            )
                    }
                    is Result.Error -> UiState.Error(imageResult.title, imageResult.text)
                }

                photo.copy(imageState = newState)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }
}