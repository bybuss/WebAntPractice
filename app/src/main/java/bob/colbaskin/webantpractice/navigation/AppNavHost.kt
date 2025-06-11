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
import bob.colbaskin.webantpractice.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.UserPreferences

@Composable
fun AppNavHost(uiState: UiState.Success<UserPreferences>) {

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentDestination = currentBackStack?.destination
    val currentGraph = currentDestination?.parent?.route
    val isBottomBarVisible = currentGraph == Graphs.Main::class.simpleName
    val currentScreen: Screens? = currentDestination?.getCurrentScreen()

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
        }
    ) { innerPadding ->
        NavHost(
            startDestination = getDestination(uiState.data.authStatus),
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        ) {
            onboardingGraph(navController, uiState.data.onboardingStatus, snackbarHostState)
            mainGraph(navController)
            detailedGraph(navController)
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
        val routeBase = route.substringBefore("/")
        when (routeBase) {
            Screens.SignIn::class.simpleName -> Screens.SignIn
            Screens.SignUp::class.simpleName -> Screens.SignUp
            Screens.Home::class.simpleName -> Screens.Home
            Screens.AddPhoto::class.simpleName -> Screens.AddPhoto
            Screens.Profile::class.simpleName -> Screens.Profile
            Screens.ViewingPhoto::class.simpleName -> {
                val id = route.substringAfter("/").takeIf { it.isNotBlank() } ?: ""
                Screens.ViewingPhoto(id)
            }
            Screens.EditingPhoto::class.simpleName -> {
                val id = route.substringAfter("/").takeIf { it.isNotBlank() } ?: ""
                Screens.EditingPhoto(id)
            }
            Screens.AddPhotoData::class.simpleName -> Screens.AddPhotoData
            Screens.Settings::class.simpleName -> Screens.Settings
            Screens.ChangePassword::class.simpleName -> Screens.ChangePassword
            else -> null
        }
    }
}
