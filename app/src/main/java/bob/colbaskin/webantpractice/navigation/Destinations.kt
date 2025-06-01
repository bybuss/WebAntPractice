package bob.colbaskin.webantpractice.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import bob.colbaskin.webantpractice.R

enum class Destinations(
    @DrawableRes val icon: Int,
    @StringRes val label: Int? = null,
    val screen: Screens
) {
    HOME(
        icon = R.drawable.home,
        label = null,
        screen = Screens.Home
    ),
    ALLPHOTOS(
        icon = R.drawable.camera,
        label = null,
        screen = Screens.AllPhotos
    ),
    PROFILE(
        icon = R.drawable.profile,
        label = null,
        screen = Screens.Profile
    ),

    PHOTOS(
        icon = R.drawable.photo,
        label = R.string.nav_photos,
        screen = Screens.Photos
    ),
    ALBUMS(
        icon = R.drawable.folder,
        label = R.string.nav_albums,
        screen = Screens.Albums
    ),
}
