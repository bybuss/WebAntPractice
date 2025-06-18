package bob.colbaskin.webantpractice.add_photo.presentation.add_data

import android.net.Uri

interface AddDataAction {
    data class UpdateUri(val uri: Uri): AddDataAction
    data class AddNewPhoto(val fileId: Int): AddDataAction
    data class UpdateImageName(val name: String): AddDataAction
    data class UpdateImageDescription(val description: String): AddDataAction
    data class UpdateImageIsNew(val isNew: Boolean): AddDataAction
    data class UpdateImageIsPopular(val isPopular: Boolean): AddDataAction
}
