package bob.colbaskin.webantpractice.profile.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController

@Composable
fun ProfileScreenRoot(
    navController: NavHostController,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "ProfileScreen")
    }
}

@Composable
private fun ProfileScreen() {
}