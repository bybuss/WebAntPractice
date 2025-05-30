package bob.colbaskin.webantpractice.design_system

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.navigation.Destinations

enum class StatusBarType {
    BackTextSearch,
    SearchOnly,
    CenteredText,
    BackText,
    TextWithAction,
    BackOnly,
    BackWithMenu
}

@Composable
fun StatusBar(
    type: StatusBarType,
    modifier: Modifier = Modifier,
    @StringRes title: Int? = null,
    onBackClick: () -> Unit = {},

    searchTextFieldState: TextFieldState? = null,
    onSearch: (String) -> Unit = {},
    searchResults: List<String> = emptyList(),

    onActionClick: () -> Unit = {},
    @DrawableRes actionIcon: Int? = null
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        when (type) {
            StatusBarType.BackTextSearch -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    }
                    Row {  }
                }
            }
            StatusBarType.SearchOnly -> TODO()
            StatusBarType.CenteredText -> TODO()
            StatusBarType.BackText -> TODO()
            StatusBarType.TextWithAction -> TODO()
            StatusBarType.BackOnly -> TODO()
            StatusBarType.BackWithMenu -> TODO()
        }
    }
}

enum class BottomBarType {
    MAIN,
    Photos
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    type: BottomBarType = BottomBarType.MAIN,
    navController: NavHostController
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val items: List<Destinations> = when (type) {
        BottomBarType.MAIN -> listOf(
            Destinations.HOME,
            Destinations.ALLPHOTOS,
            Destinations.PROFILE
        )
        BottomBarType.Photos -> listOf(
            Destinations.PHOTOS,
            Destinations.ALBUMS
        )
    }

    NavigationBar(
        containerColor = CustomTheme.colors.white,
        contentColor = CustomTheme.colors.graySecondary,
        modifier = modifier
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        items.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any {
                it.hasRoute(destination.screen::class)
            } == true
            
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(destination.screen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.label?.let { stringResource(it) }
                    )
                },
                modifier = Modifier,
                label = { destination.label?.let { label ->
                    Text(text = stringResource(label))
                } },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CustomTheme.colors.main,
                    selectedTextColor = CustomTheme.colors.main,
                    unselectedIconColor = CustomTheme.colors.graySecondary,
                    unselectedTextColor = CustomTheme.colors.graySecondary,
                    disabledIconColor = CustomTheme.colors.gray,
                    disabledTextColor = CustomTheme.colors.gray,
                    indicatorColor = Color.Transparent
                ),
                interactionSource = interactionSource
            )
        }
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    WebAntPracticeTheme {
        BottomBar(
            type = BottomBarType.Photos,
            navController = rememberNavController()
        )
    }
}