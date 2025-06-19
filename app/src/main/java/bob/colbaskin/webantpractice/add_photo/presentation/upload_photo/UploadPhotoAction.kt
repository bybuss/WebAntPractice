package bob.colbaskin.webantpractice.add_photo.presentation.upload_photo

import android.net.Uri

sealed interface UploadPhotoAction {
    data class SelectFileFromGallery(val uri: Uri): UploadPhotoAction
    data object UploadFile: UploadPhotoAction
    data class ToAddPhotoData(val fileId: Int, val imageUri: Uri): UploadPhotoAction
    data object ResetNavigation: UploadPhotoAction
}
