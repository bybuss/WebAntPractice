package bob.colbaskin.webantpractice.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.BottomBar
import bob.colbaskin.webantpractice.common.design_system.CustomSnackbarHost
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.UserPreferences

@Composable
fun AppNavHost(uiState: UiState.Success<UserPreferences>) {

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentDestination = currentBackStack?.destination
    val currentGraph = currentDestination?.parent?.route
    val isBottomBarVisible = currentGraph == Graphs.Main::class.qualifiedName
    val currentScreen: Screens? = currentDestination?.getCurrentScreen()
    val initialOnboardingStatus = remember { uiState.data.onboardingStatus }

    Scaffold (
        topBar = {
            key (currentScreen) {
                TopBar(currentScreen = currentScreen, navController = navController)
            }
        },
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            AnimatedVisibility(visible = isBottomBarVisible) {
                BottomBar(navController = navController)
            }
        },
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        NavHost(
            startDestination = getDestination(uiState.data.authStatus),
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        ) {
            onboardingGraph(navController, initialOnboardingStatus, snackbarHostState)
            mainGraph(navController, snackbarHostState)
            detailedGraph(navController, snackbarHostState)
        }
    }
}

private fun getDestination(status: AuthConfig): Graphs {
    return when (status) {
        AuthConfig.AUTHENTICATED -> Graphs.Main
        else -> Graphs.Onboarding
    }
}

private fun NavDestination.getCurrentScreen(): Screens? {
    return route?.let { route ->
        val segments: List<String> = route.split("/")
        val lastSegment: String? = segments.lastOrNull()
        when (lastSegment) {
            Screens.SignIn::class.qualifiedName -> Screens.SignIn
            Screens.SignUp::class.qualifiedName -> Screens.SignUp
            Screens.Home::class.qualifiedName -> Screens.Home
            Screens.UploadPhoto::class.qualifiedName -> Screens.UploadPhoto
            Screens.Profile::class.qualifiedName -> Screens.Profile
            Screens.Settings::class.qualifiedName -> Screens.Settings
            Screens.ChangePassword::class.qualifiedName -> Screens.ChangePassword
            else -> {
                when {
                    segments.size > 1 && segments[0] == "viewingPhoto" -> {
                        val id = segments[1].toIntOrNull()
                        Screens.ViewingPhoto(id ?: -1)
                    }
                    segments.size > 1 && segments[0] == "editingPhoto" -> {
                        val id = segments[1].toIntOrNull()
                        Screens.EditingPhoto(id ?: -1)
                    }
                    segments.size > 1 && segments[0] == "addPhotoData" -> {
                        val fileId = segments[1].toIntOrNull()
                        val imageUri = segments[2].toString()
                        Screens.AddPhotoData(fileId ?: -1, imageUri)
                    }
                    else -> null
                }
            }
        }
    }
}
