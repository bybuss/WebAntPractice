package bob.colbaskin.webantpractice.home.presentation.editing_photo


interface EditingPhotoAction {
    data class GetPhotoById(val id: Int): EditingPhotoAction
    data class UpdatePhoto(
        val id: Int,
        val fileId: Int,
        val userId: Int,
        val description: String,
        val name: String,
        val isNew: Boolean,
        val isPopular: Boolean
    ): EditingPhotoAction
    data class UpdateImageName(val name: String): EditingPhotoAction
    data class UpdateImageDescription(val description: String): EditingPhotoAction
    data class UpdateImageIsNew(val isNew: Boolean): EditingPhotoAction
    data class UpdateImageIsPopular(val isPopular: Boolean): EditingPhotoAction
}
