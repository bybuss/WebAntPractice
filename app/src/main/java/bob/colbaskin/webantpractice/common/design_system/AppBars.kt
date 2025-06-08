package bob.colbaskin.webantpractice.common.design_system

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.navigation.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTextSearchTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    searchTextFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(CustomTheme.colors.white)) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = CustomTheme.colors.black
                )
            },
            navigationIcon = {
                CustomIconButton(
                    painterId = R.drawable.back,
                    contentDescriptionId = stringResource(R.string.back_arrow_logo_description),
                    onClick = onBackClick
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CustomTheme.colors.white,
                navigationIconContentColor = CustomTheme.colors.graySecondary,
                titleContentColor = CustomTheme.colors.black,
            )
        )
        SearchOnlyTopAppBar(
            searchTextFieldState = searchTextFieldState,
            onSearch = onSearch,
            searchResults = searchResults,
        )
    }
}

@Composable
fun SearchOnlyTopAppBar(
    searchTextFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.white)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Search(
            textFieldState = searchTextFieldState,
            onSearch = onSearch,
            searchResults = searchResults,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredTextTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    Box {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = CustomTheme.colors.white,
                titleContentColor = CustomTheme.colors.black,
            )
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = CustomTheme.colors.gray,
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTextTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                CustomIconButton(
                    painterId = R.drawable.back,
                    contentDescriptionId = stringResource(R.string.back_arrow_logo_description),
                    onClick = onBackClick
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CustomTheme.colors.white,
                navigationIconContentColor = CustomTheme.colors.graySecondary,
                titleContentColor = CustomTheme.colors.black,
            )
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = CustomTheme.colors.gray,
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextWithActionTopAppBar(
    title: String,
    actionButtonLabel: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            actions = { CustomTextButton(text = actionButtonLabel, onClick = onActionClick) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = CustomTheme.colors.white,
                titleContentColor = CustomTheme.colors.black,
                actionIconContentColor = CustomTheme.colors.main
            )
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = CustomTheme.colors.gray,
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackOnlyTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        TopAppBar(
            modifier = modifier,
            title = {},
            navigationIcon = {
                CustomIconButton(
                    painterId = R.drawable.back,
                    contentDescriptionId = stringResource(R.string.back_arrow_logo_description),
                    onClick = onBackClick
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CustomTheme.colors.white,
                navigationIconContentColor = CustomTheme.colors.graySecondary,
            )
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = CustomTheme.colors.gray,
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackWithMenuTopAppBar(
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        TopAppBar(
            modifier = modifier,
            title = {},
            navigationIcon = {
                CustomIconButton(
                    painterId = R.drawable.back,
                    contentDescriptionId = stringResource(R.string.back_arrow_logo_description),
                    onClick = onBackClick
                )
            },
            actions = {
                CustomIconButton(
                    painterId = R.drawable.more_vert,
                    contentDescriptionId = stringResource(R.string.more_vert_logo_description),
                    onClick = onMenuClick
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CustomTheme.colors.white,
                navigationIconContentColor = CustomTheme.colors.graySecondary,
                titleContentColor = CustomTheme.colors.black,
                actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = CustomTheme.colors.gray,
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloseTextCheckTopAppBar(
    title: String,
    onCloseClick: () -> Unit,
    onCheckClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            CustomIconButton(
                painterId = R.drawable.close,
                contentDescriptionId = stringResource(R.string.close_logo_description),
                onClick = onCloseClick
            )
        },
        actions = {
            CustomIconButton(
                painterId = R.drawable.status_bar_check_mark,
                contentDescriptionId = stringResource(R.string.check_circle_filled_logo_description),
                onClick = onCheckClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomTheme.colors.white,
            navigationIconContentColor = CustomTheme.colors.gray,
            titleContentColor = CustomTheme.colors.black,
            actionIconContentColor = CustomTheme.colors.main
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        actions = {
            CustomIconButton(
                painterId = R.drawable.settings,
                contentDescriptionId = stringResource(R.string.settings_logo_description),
                onClick = onSettingsClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomTheme.colors.white,
            titleContentColor = CustomTheme.colors.black,
            actionIconContentColor = CustomTheme.colors.gray
        )
    )
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val items: List<Destinations> = listOf(
            Destinations.HOME,
            Destinations.ALL_PHOTOS,
            Destinations.PROFILE
    )

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
                        contentDescription = stringResource(destination.label)
                    )
                },
                modifier = Modifier,
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

@Preview(showBackground = true)
@Composable
private fun BackTextSearchPreview() {
    val textState = rememberTextFieldState("")
    var results by remember { mutableStateOf(listOf<String>()) }

    WebAntPracticeTheme {
        BackTextSearchTopAppBar(
            title = stringResource(R.string.all_photos_top_bar_title),
            onBackClick = {},
            searchTextFieldState = textState,
            onSearch = { query ->
                results = if (query.isNotEmpty()) {
                    listOf("Product 1", "Product 2", "Product 3")
                        .filter { it.contains(query, ignoreCase = true) }
                } else emptyList()
            },
            searchResults = results
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchOnlyPreview() {
    val textState = rememberTextFieldState("")
    var results by remember { mutableStateOf(listOf<String>()) }

    WebAntPracticeTheme {
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
}

@Preview(showBackground = true)
@Composable
private fun CenteredTextPreview() {
    WebAntPracticeTheme {
        CenteredTextTopAppBar(title = stringResource(R.string.all_photos_top_bar_title))
    }
}

@Preview(showBackground = true)
@Composable
private fun BackTextPreview() {
    WebAntPracticeTheme {
        BackTextTopAppBar(
            title = stringResource(R.string.all_photos_top_bar_title),
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextWithActionPreview() {
    WebAntPracticeTheme {
        TextWithActionTopAppBar(
            title = stringResource(R.string.all_photos_top_bar_title),
            actionButtonLabel = stringResource(R.string.sign_in),
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BackOnlyPreview() {
    WebAntPracticeTheme {
        BackOnlyTopAppBar({})
    }
}

@Preview(showBackground = true)
@Composable
private fun BackWithMenuPreview() {
    WebAntPracticeTheme {
        BackWithMenuTopAppBar({}, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun CloseTextCheckPreview() {
    WebAntPracticeTheme {
        CloseTextCheckTopAppBar(
            title = stringResource(R.string.all_photos_top_bar_title),
            onCloseClick = {},
            onCheckClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsPreview() {
    WebAntPracticeTheme {
        SettingsTopAppBar({})
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    WebAntPracticeTheme { BottomBar(navController = rememberNavController()) }
}