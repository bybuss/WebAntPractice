package bob.colbaskin.webantpractice.home.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.toUiState
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
    private val imageStates = mutableStateMapOf<String, UiState<ImageBitmap>>()

    init {
        loadPhotos(new = true, popular = false)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.TabSelected -> handleTabSelected(action.index)
            else -> null
        }
    }

    suspend fun loadImage(photo: Photo): UiState<ImageBitmap> {
        return photosRepository.loadImage(photo).also { state ->
            imageStates[photo.file.path] = state.toUiState()
        }.toUiState()
    }

    fun getImageState(path: String): UiState<ImageBitmap> {
        return imageStates[path] ?: UiState.Loading
    }

    private fun loadPhotos(new: Boolean?, popular: Boolean?) {
        state = state.copy(photos = UiState.Loading)
        viewModelScope.launch {
            val flow = photosRepository.getPhotosStream(
                new = new,
                popular = popular
            ).toUiState()
            state = state.copy(photos = flow)
        }
    }

    private fun handleTabSelected(index: Int) {
        state = state.copy(selectedIndex = index)
        when (index) {
            0 -> loadPhotos(new = true, popular = null)
            1 -> loadPhotos(new = null, popular = true)
        }
    }
}
