package bob.colbaskin.webantpractice.home.presentation.viewing_photo

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController

@Composable
fun ViewingPhotoScreenRoot(
    navController: NavHostController,
) {
    val photoId: Int
        = navController.currentBackStackEntry?.arguments?.getString("id")?.toIntOrNull() ?: -1

    Box(contentAlignment = Alignment.Center) {
        Text(text = "ViewingPhotoScreen: $photoId")
    }
}

@Composable
private fun ViewingPhotoScreen() {
}