package bob.colbaskin.webantpractice.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import bob.colbaskin.webantpractice.R

enum class Destinations(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val screen: Screens
) {
    HOME(
        icon = R.drawable.home,
        label = R.string.nav_home,
        screen = Screens.Home
    ),
    ALL_PHOTOS(
        icon = R.drawable.camera,
        label = R.string.nav_all_photos,
        screen = Screens.AllPhotos
    ),
    PROFILE(
        icon = R.drawable.profile,
        label = R.string.nav_profile,
        screen = Screens.Profile
    )
}
