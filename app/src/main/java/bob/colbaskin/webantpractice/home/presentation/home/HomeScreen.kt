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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.ErrorIndicator
import bob.colbaskin.webantpractice.common.design_system.LoadingIndicator
import bob.colbaskin.webantpractice.common.design_system.TabButton
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.navigation.Screens
import kotlinx.coroutines.flow.Flow

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
                        onClick = { viewModel.onAction(HomeAction.TabSelected(index)) },
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
    viewModel: HomeViewModel = hiltViewModel(),
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
            photosFlow = photosState.data,
            viewModel = viewModel,
            onAction = onAction
        )
    }
}

@Composable
private fun PhotosGrid(
    photosFlow: Flow<PagingData<Photo>>,
    viewModel: HomeViewModel,
    onAction: (HomeAction) -> Unit
) {
    val lazyPagingItems: LazyPagingItems<Photo> = photosFlow.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            val photo = lazyPagingItems[index] ?: return@items
            PhotoItem(
                photo = photo,
                viewModel = viewModel,
                onClick = { onAction(HomeAction.ViewPhoto(id = photo.id)) }
            )
        }
    }
}

@Composable
private fun PhotoItem(
    photo: Photo,
    viewModel: HomeViewModel,
    onClick: () -> Unit
) {
    val path = photo.file.path
    var imageState: UiState<ImageBitmap> by remember { mutableStateOf(UiState.Loading) }

    LaunchedEffect(path) {
        imageState = viewModel.getImageState(path)
        if (imageState !is UiState.Success) {
            imageState = try {
                viewModel.loadImage(photo)
            } catch (e: Exception) {
                UiState.Error("Error", e.message ?: "Unknown error")
            }
        }
    }

    Box(
        modifier = Modifier
            .size(156.dp)
            .clip(CustomTheme.shapes.photoBig)
            .background(CustomTheme.colors.grayLight)
            .clickable(onClick = onClick)
    ) {
        when (imageState) {
            is UiState.Loading -> LoadingIndicator(
                modifier = Modifier.fillMaxSize(),
                isIndicatorOnly = true
            )
            is UiState.Error -> ErrorPlaceholder()
            is UiState.Success -> {
                val image = (imageState as UiState.Success<ImageBitmap>).data
                Image(
                    bitmap = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
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
