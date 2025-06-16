package bob.colbaskin.webantpractice.common.editing_photo

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
import bob.colbaskin.webantpractice.home.domain.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditingPhotoViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    var state by mutableStateOf(EditingPhotoState())
        private set

    fun onAction(action: EditingPhotoAction) {
        when (action) {
            is EditingPhotoAction.GetPhotoById -> getPhotoById(action.id)
            is EditingPhotoAction.UpdatePhoto -> updatePhoto(
                id = action.id,
                fileId = action.fileId,
                userId = action.userId,
                description = action.description,
                name = action.name,
                isNew = action.isNew,
                isPopular = action.isPopular
            )
            is EditingPhotoAction.UpdateImageName -> updateImageName(action.name)
            is EditingPhotoAction.UpdateImageDescription -> updateImageDescription(action.description)
            is EditingPhotoAction.UpdateImageIsNew -> updateImageIsNew(action.isNew)
            is EditingPhotoAction.UpdateImageIsPopular -> updateImageIsPopular(action.isPopular)
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
                    state.copy(UiState.Success(fullPhotoData))
                        .also { getImage(path = fullPhotoData.file.path) }
                }
                else -> state.copy(fullPhoto = response)
            }
        }
    }

    private fun getImage(path: String) {
        state = state.copy(UiState.Loading)
        viewModelScope.launch {
            val response = photosRepository.getFile(path)
            val imageBitMap = response.toImageBitmap(context)
            state = state.copy(fullPhoto = state.fullPhoto.updateIfSuccess {
                copy(imageState = imageBitMap)
            })
        }
    }

    private fun updatePhoto(
        id: Int,
        fileId: Int,
        userId: Int,
        description: String,
        name: String,
        isNew: Boolean,
        isPopular: Boolean
    ) {
        state = state.copy(UiState.Loading)
        viewModelScope.launch {
            val response = photosRepository.updatePhoto(
                id = id,
                fileId = fileId,
                userId = userId,
                description = description,
                name = name,
                isNew = isNew,
                isPopular = isPopular
            ).toUiState()

            state = when (response) {
                is UiState.Success -> {
                    val fullPhotoData = response.data
                    state.copy(UiState.Success(fullPhotoData))
                        .also { getImage(path = fullPhotoData.file.path) }
                }
                else -> state.copy(fullPhoto = response)
            }
        }
    }

    private fun updateImageName(name: String) {
        state = state.copy(fullPhoto = state.fullPhoto.updateIfSuccess { copy(name = name) })
    }

    private fun updateImageDescription(description: String) {
        state = state.copy(
            fullPhoto = state.fullPhoto.updateIfSuccess { copy(description = description) }
        )
    }

    private fun updateImageIsNew(isNew: Boolean) {
        state = state.copy(fullPhoto = state.fullPhoto.updateIfSuccess { copy(new = isNew) })
    }


    private fun updateImageIsPopular(isPopular: Boolean) {
        state = state.copy(
            fullPhoto = state.fullPhoto.updateIfSuccess { copy(popular = isPopular) }
        )
    }
}