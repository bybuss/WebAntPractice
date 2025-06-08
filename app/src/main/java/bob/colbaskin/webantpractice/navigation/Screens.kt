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
    data object AllPhotos: Screens

    @Serializable
    data object Profile: Screens
}