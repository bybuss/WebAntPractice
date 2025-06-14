package bob.colbaskin.webantpractice.home.presentation.viewing_photo

interface ViewingPhotoAction {
    data class GetPhotoById(val id: Int): ViewingPhotoAction
}