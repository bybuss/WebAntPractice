package bob.colbaskin.webantpractice.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Welcome: Screens

    @Serializable
    data object Login: Screens

    @Serializable
    data object Register: Screens

    @Serializable
    data object Home: Screens

    @Serializable
    data object AllPhotos: Screens

    @Serializable
    data object Profile: Screens

    @Serializable
    data object Photos: Screens

    @Serializable
    data object Albums: Screens
}