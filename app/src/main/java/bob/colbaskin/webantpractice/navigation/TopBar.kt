package bob.colbaskin.webantpractice.navigation

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.BackTextTopAppBar
import bob.colbaskin.webantpractice.common.design_system.BackWithMenuTopAppBar
import bob.colbaskin.webantpractice.common.design_system.Dropdown
import bob.colbaskin.webantpractice.common.design_system.SearchOnlyTopAppBar
import bob.colbaskin.webantpractice.common.design_system.SettingsTopAppBar
import bob.colbaskin.webantpractice.common.design_system.TextWithActionTopAppBar

@Composable
fun TopBar(
    currentScreen: Screens?,
    navController: NavHostController
) {
    when (currentScreen) {
        Screens.Home -> {
            val textState = rememberTextFieldState("")
            var results by remember { mutableStateOf(listOf<String>()) }
            SearchOnlyTopAppBar(
                searchTextFieldState = textState,
                onSearch = { query ->
                    results = if (query.isNotEmpty()) {
                        listOf("Result 1", "Result 2", "Result 3")
                            .filter { it.contains(query, ignoreCase = true) }
                    } else emptyList()
                },
                searchResults = results
            )
        }
        Screens.AddPhoto -> {
            TextWithActionTopAppBar(
                title = stringResource(R.string.top_bar_title_all_photos),
                actionButtonLabel = stringResource(R.string.next),
                onActionClick = { navController.navigate(Screens.AddPhotoData) }
            )
        }
        Screens.AddPhotoData -> {
            BackTextTopAppBar(
                title = stringResource(R.string.top_bar_title_new_photo),
                onBackClick = { navController.popBackStack() },
            )
        }
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
   