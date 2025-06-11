package bob.colbaskin.webantpractice.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import bob.colbaskin.webantpractice.add_photo.presentation.AddPhotoScreenRoot
import bob.colbaskin.webantpractice.auth.presentation.sign_in.SignInScreenRoot
import bob.colbaskin.webantpractice.auth.presentation.sign_up.SignUpScreenRoot
import bob.colbaskin.webantpractice.common.editing_photo.EditingPhotoScreenRoot
import bob.colbaskin.webantpractice.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.webantpractice.home.presentation.home.HomeScreenRoot
import bob.colbaskin.webantpractice.home.presentation.viewing_photo.ViewingPhotoScreenRoot
import bob.colbaskin.webantpractice.onboarding.presentation.WelcomeScreenRoot
import bob.colbaskin.webantpractice.profile.presentation.change_password.ChangePasswordScreenRoot
import bob.colbaskin.webantpractice.profile.presentation.profile.ProfileScreenRoot
import bob.colbaskin.webantpractice.profile.presentation.settings.SettingsScreenRoot

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    onboardingStatus: OnboardingConfig,
    snackbarHostState: SnackbarHostState
) {
    navigation<Graphs.Onboarding> (startDestination = getStartDestination(onboardingStatus)) {
        animatedTransition<Screens.Welcome> { WelcomeScreenRoot(navController) }
        animatedTransition<Screens.SignIn> { SignInScreenRoot(navController, snackbarHostState) }
        animatedTransition<Screens.SignUp> { SignUpScreenRoot(navController, snackbarHostState) }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation<Graphs.Main>(startDestination = Screens.Home) {
        animatedTransition<Screens.Home> { HomeScreenRoot(navController) }
        animatedTransition<Screens.AddPhoto> { AddPhotoScreenRoot(navController) }
        animatedTransition<Screens.Profile> { ProfileScreenRoot(navController) }
    }
}

fun NavGraphBuilder.detailedGraph(navController: NavHostController) {
    navigation<Graphs.Detailed> (startDestination = Screens.Settings) {
        animatedTransition<Screens.ViewingPhoto> {
            ViewingPhotoScreenRoot(navController)
        }
        animatedTransition<Screens.EditingPhoto> {
            EditingPhotoScreenRoot(navController)
        }
        animatedTransition<Screens.AddPhotoData> { AddPhotoScreenRoot(navController) }
        animatedTransition<Screens.Settings> { SettingsScreenRoot(navController) }
        animatedTransition<Screens.ChangePassword> { ChangePasswordScreenRoot(navController) }
    }
}

private fun getStartDestination(status: OnboardingConfig) = when (status) {
    OnboardingConfig.COMPLETED -> Screens.SignIn
    else -> Screens.Welcome
}