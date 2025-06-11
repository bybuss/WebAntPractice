package bob.colbaskin.webantpractice.profile.presentation.change_password

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController

@Composable
fun ChangePasswordScreenRoot(
    navController: NavHostController,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "ChangePasswordScreen")
    }
}

@Composable
private fun ChangePasswordScreen() {
}