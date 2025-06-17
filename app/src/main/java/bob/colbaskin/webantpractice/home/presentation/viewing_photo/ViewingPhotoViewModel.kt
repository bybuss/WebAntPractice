package bob.colbaskin.webantpractice.home.presentation.viewing_photo

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.toUiState
import bob.colbaskin.webantpractice.common.updateIfSuccess
import bob.colbaskin.webantpractice.common.utils.toImageBitmap
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewingPhotoViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    var state by mutableStateOf(ViewingPhotoState())
        private set

    fun onAction(action: ViewingPhotoAction) {
        when (action) {
            is ViewingPhotoAction.GetPhotoById -> getPhotoById(action.id)
            else -> Unit
        }
    }

    private fun getPhotoById(id: Int) {
        state = state.copy(UiState.Loading)
        viewModelScope.launch {
            val response = photosRepository.getPhotoById(id).toUiState()
            state = when (response) {
                is UiState.Success -> {
                    val fullPhotoData = response.data
                    state.copy(fullPhoto = UiState.Success(fullPhotoData))
                        .also { getImage(fullPhotoData.file.path) }
                }
                else -> state.copy(fullPhoto = response)
            }
        }
    }

    private fun getImage(path: String) {
        state = state.copy(UiState.Loading)
        viewModelScope.launch {
            val response = photosRepository.getFile(path = path)
            val imageBitMap = response.toImageBitmap(context)
            state = state.copy(fullPhoto = state.fullPhoto.updateIfSuccess {
                copy(imageState = imageBitMap)
            })
        }
    }
}