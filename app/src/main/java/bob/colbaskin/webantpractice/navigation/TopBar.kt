package bob.colbaskin.webantpractice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.BackTextTopAppBar
import bob.colbaskin.webantpractice.common.design_system.SettingsTopAppBar

@Composable
fun TopBar(
    currentScreen: Screens?,
    navController: NavHostController
) {
    when (currentScreen) {
        Screens.Profile -> {
            SettingsTopAppBar(
                onSettingsClick = { navController.navigate(Screens.Settings) }
            )
        }
        Screens.Settings -> {
            BackTextTopAppBar(
                title = stringResource(R.string.top_bar_title_settings),
                onBackClick = { navController.popBackStack() },
            )
        }
        Screens.ChangePassword -> {
            BackTextTopAppBar(
                title = stringResource(R.string.top_bar_title_new_password),
                onBackClick = { navController.popBackStack() },
            )
        }
        else -> null
    }
}
   