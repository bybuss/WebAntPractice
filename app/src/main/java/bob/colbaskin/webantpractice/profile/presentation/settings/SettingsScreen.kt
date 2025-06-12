package bob.colbaskin.webantpractice.profile.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController

@Composable
fun SettingsScreenRoot(
    navController: NavHostController,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "SettingsScreen")
    }
}

@Composable
private fun SettingsScreen() {
}