package bob.colbaskin.webantpractice.add_photo.presentation.upload_photo

import android.net.Uri
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.home.domain.models.PhotoFile

data class UploadPhotoState(
    val selectedImage: Uri? = null,
    val file: UiState<PhotoFile> = UiState.Loading,
    val isLoading: Boolean = false,
    val navigationEvent: UploadPhotoAction.ToAddPhotoData? = null
)
