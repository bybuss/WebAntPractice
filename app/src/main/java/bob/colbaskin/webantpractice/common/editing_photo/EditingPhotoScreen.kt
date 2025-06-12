package bob.colbaskin.webantpractice.common.editing_photo

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController

@Composable
fun EditingPhotoScreenRoot(
    navController: NavHostController,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "EditingPhotoScreen")
    }
}

@Composable
private fun EditingPhotoScreen() {
}