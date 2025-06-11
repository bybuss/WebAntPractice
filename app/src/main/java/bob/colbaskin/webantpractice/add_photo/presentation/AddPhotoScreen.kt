package bob.colbaskin.webantpractice.add_photo.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController

@Composable
fun AddPhotoScreenRoot(
    navController: NavHostController,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "AddPhotoScreen")
    }
}

@Composable
private fun AddPhotoScreen() {
}