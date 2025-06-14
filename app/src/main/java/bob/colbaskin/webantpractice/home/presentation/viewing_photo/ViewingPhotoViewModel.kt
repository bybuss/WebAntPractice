package bob.colbaskin.webantpractice.home.presentation.viewing_photo

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.toUiState
import bob.colbaskin.webantpractice.home.domain.PhotosRepository
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
                    state.copy(fullPhoto = UiState.Success(fullPhotoData.copy()))
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

            val imageState = when (response) {
                is Result.Success -> {
                    val bitmap = BitmapFactory.decodeByteArray(
                        response.data,
                        0,
                        response.data.size
                    )
                    bitmap?.asImageBitmap()?.let { UiState.Success(it) }
                        ?: UiState.Error(
                            title = context.getString(R.string.error_title),
                            text = context.getString(R.string.error_text)
                        )
                }
                is Result.Error -> UiState.Error(response.title, response.text)
            }

            state = state.let { currentState ->
                val currentFullPhoto = currentState.fullPhoto
                if (currentFullPhoto is UiState.Success) {
                    currentState.copy(fullPhoto = UiState.Success(
                        currentFullPhoto.data.copy(imageState = imageState)
                    ))
                } else { currentState }
            }
        }
    }
}