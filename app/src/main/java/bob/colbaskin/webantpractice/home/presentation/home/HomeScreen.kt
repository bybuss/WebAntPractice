package bob.colbaskin.webantpractice.home.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.ErrorIndicator
import bob.colbaskin.webantpractice.common.design_system.LoadingIndicator
import bob.colbaskin.webantpractice.common.design_system.TabButton
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.navigation.Screens

@Composable
fun HomeScreenRoot(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val tabs: List<String> = listOf(
        stringResource(R.string.tab_new),
        stringResource(R.string.tab_popular),
    )

    Scaffold (
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(40.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    TabButton(
                        text = title,
                        onClick = {
                            viewModel.onAction(HomeAction.TabSelected(index))
                            when (index) {
                                0 -> viewModel.onAction(HomeAction.LoadPhotos(new = true))
                                1 -> viewModel.onAction(HomeAction.LoadPhotos(popular = true))
                            }
                        },
                        modifier = Modifier.weight(1f),
                        isSelected = index == state.selectedIndex
                    )
                }
            }
        },
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            HomeScreen(
                state = state,
                onAction = { action ->
                    when (action) {
                        is HomeAction.ViewPhoto -> navController.navigate(
                            Screens.ViewingPhoto(id = action.id)
                        )
                        else -> Unit
                    }
                    viewModel.onAction(action)
                }
            )
        }
    }
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val photosState = state.photos

    when (photosState) {
        is UiState.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
        is UiState.Error -> ErrorIndicator(
            title = photosState.title,
            text = photosState.text,
            modifier = Modifier.fillMaxSize()
        )
        is UiState.Success -> PhotosGrid(
            photos = photosState.data,
            onAction = onAction
        )
    }
}

@Composable
private fun PhotosGrid(
    photos: List<Photo>,
    onAction: (HomeAction) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(photos) { photo ->
            Box(
                modifier = Modifier
                    .size(156.dp)
                    .clip(CustomTheme.shapes.photoBig)
                    .background(CustomTheme.colors.grayLight)
                    .clickable(onClick = { onAction(HomeAction.ViewPhoto(id = photo.id)) })
            ) {
                when (val imageState = photo.imageState) {
                    is UiState.Loading -> LoadingIndicator(
                        modifier = Modifier.fillMaxSize(),
                        isIndicatorOnly = true
                    )
                    is UiState.Error -> ErrorPlaceholder()
                    is UiState.Success -> Image(
                        bitmap = imageState.data,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.grayLight),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.webant_error_logo),
            contentDescription = null
        )
    }
}
