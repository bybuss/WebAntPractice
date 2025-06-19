package bob.colbaskin.webantpractice.add_photo.presentation.add_data

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.auth.domain.auth.AuthRepository
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosRepository
import bob.colbaskin.webantpractice.common.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDataViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    var state by mutableStateOf(AddDataState())
        private set

    fun onAction(action: AddDataAction) {
        when (action) {
            is AddDataAction.AddNewPhoto -> addNewPhoto(action.fileId)
            is AddDataAction.UpdateUri -> updateUri(action.uri)
            is AddDataAction.UpdateImageName -> updateImageName(action.name)
            is AddDataAction.UpdateImageDescription -> updateImageDescription(action.description)
            is AddDataAction.UpdateImageIsNew -> updateImageIsNew(action.isNew)
            is AddDataAction.UpdateImageIsPopular -> updateImageIsPopular(action.isPopular)
            else -> Unit
        }
    }

    private fun addNewPhoto(fileId: Int) {
        state = state.copy(fullPhoto = UiState.Loading, isLoading = true)
        viewModelScope.launch {
            val userId = authRepository.getCurrentUser().toUiState()
            if (userId is UiState.Success) {
                val response = photosRepository.createPhoto(
                    fileId = fileId,
                    userId = userId.data.id,
                    description = state.description,
                    name = state.name,
                    isNew = state.isNew,
                    isPopular = state.isPopular
                ).toUiState()
                state = state.copy(fullPhoto = response, isLoading = false)
            }
        }
    }

    private fun updateUri(uri: Uri) {
        state = state.copy(image = uri)
    }

    private fun updateImageName(name: String) {
        state = state.copy(name = name)
    }

    private fun updateImageDescription(description: String) {
        state = state.copy(description = description)
    }

    private fun updateImageIsNew(isNew: Boolean) {
        state = state.copy(isNew = isNew)
    }


    private fun updateImageIsPopular(isPopular: Boolean) {
        state = state.copy(isPopular = isPopular)
    }
}
