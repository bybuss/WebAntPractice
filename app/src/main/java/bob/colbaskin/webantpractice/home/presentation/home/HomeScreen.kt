package bob.colbaskin.webantpractice.home.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController

@Composable
fun HomeScreenRoot(
    navController: NavHostController,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "HomeScreen")
    }
}

@Composable
private fun HomeScreen() {
}