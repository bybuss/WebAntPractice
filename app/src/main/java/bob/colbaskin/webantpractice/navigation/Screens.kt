package bob.colbaskin.webantpractice.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Welcome: Screens

    @Serializable
    data object SignIn: Screens

    @Serializable
    data object SignUp: Screens

    @Serializable
    data object Home: Screens

    @Serializable
    data object UploadPhoto: Screens

    @Serializable
    data object Profile: Screens

    @Serializable
    data class ViewingPhoto(val id: Int?): Screens

    @Serializable
    data class EditingPhoto(val id: Int?): Screens

    @Serializable
    data class AddPhotoData(val fileId: Int, val imageUri: String): Screens

    @Serializable
    data object Settings: Screens

    @Serializable
    data object ChangePassword: Screens

    @Serializable
    data object SuccessChangePassword: Screens
}
