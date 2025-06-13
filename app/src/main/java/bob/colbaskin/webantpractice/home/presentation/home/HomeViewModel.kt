package bob.colbaskin.webantpractice.home.presentation.home

import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.home.domain.PhotosRepository
import bob.colbaskin.webantpractice.home.domain.models.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
): ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        loadPhotos(new = true, popular = false)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.TabSelected -> updateSelectedIndex(action.index)
            is HomeAction.LoadPhotos -> loadPhotos(action.new, action.popular)
            else -> null
        }
    }

    private fun loadPhotos(new: Boolean?, popular: Boolean?) {
        state = state.copy(photos = UiState.Loading)

        viewModelScope.launch {
            val result = photosRepository.getPhotos(
                page = 1,
                itemsPerPage = 20,
                order = null,
                new = new,
                popular = popular
            )

            when (result) {
                is Result.Success -> {
                    val photosWithImages = result.data.map { it.copy(imageState = UiState.Loading) }
                    state = state.copy(photos = UiState.Success(photosWithImages))
                    loadImagesForPhotos(photosWithImages)
                }
                is Result.Error -> {
                    state = state.copy(photos = UiState.Error(title = result.title, text = result.text))
                }
            }
        }
    }

    private fun loadImagesForPhotos(photos: List<Photo>) {
        photos.forEach { photo ->
            viewModelScope.launch {
                val imageResult = photosRepository.getFile(photo.file.path)
                updatePhotoImageState(photo.id, imageResult)
            }
        }
    }

    private fun updatePhotoImageState(photoId: Int, result: Result<ByteArray>) {
        val newState = when (result) {
            is Result.Success -> {
                val bitmap = BitmapFactory.decodeByteArray(result.data, 0, result.data.size)
                bitmap?.asImageBitmap()?.let { UiState.Success(it) }
                    ?: UiState.Error("Decoding Error", "Could not decode image")
            }
            is Result.Error -> UiState.Error(result.title, result.text)
        }

        state = state.let { currentState ->
            if (currentState.photos is UiState.Success) {
                val updatedPhotos = currentState.photos.data.map { photo ->
                    if (photo.id == photoId) photo.copy(imageState = newState) else photo
                }
                currentState.copy(photos = UiState.Success(updatedPhotos))
            } else {
                currentState
            }
        }
    }

    private fun updateSelectedIndex(selectedIndex: Int) {
        state = state.copy(selectedIndex = selectedIndex)
    }
}
