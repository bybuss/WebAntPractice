package bob.colbaskin.webantpractice.home.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.ErrorIndicator
import bob.colbaskin.webantpractice.common.design_system.LoadingIndicator
import bob.colbaskin.webantpractice.common.design_system.SearchOnlyTopAppBar
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
    val photos = state.photos[state.selectedIndex]?.collectAsLazyPagingItems()
    val searchResults: List<Photo> = remember(state.searchQuery) {
        if (state.searchQuery.isEmpty()) {
            emptyList()
        } else {
            photos?.itemSnapshotList?.mapNotNull { it }
                ?.filter { photo ->
                    photo.name.let { nameState ->
                        when (nameState) {
                            is UiState.Success -> nameState.data.contains(
                                state.searchQuery, true
                            )
                            else -> false
                        }
                    }
                } ?: emptyList()
        }
    }

    LaunchedEffect(searchResults) {
        viewModel.onAction(HomeAction.UpdateSearchResults(searchResults))
    }

    Scaffold (
        topBar = {
            Column {
                SearchOnlyTopAppBar(
                    searchTextFieldState = rememberTextFieldState(state.searchQuery),
                    onSearch = { query -> viewModel.onAction(
                        HomeAction.SearchQueryChanged(query)
                    ) },
                    searchResults = searchResults
                )
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
            }
        },
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
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
    val photos = state.photos[state.selectedIndex]?.collectAsLazyPagingItems()

    when {
        state.searchQuery.isNotEmpty() && state.searchResults.isNotEmpty() -> PhotosGrid(
            type = PhotosType.SEARCH_RESULTS,
            filteredPhotos = state.searchResults,
            onAction = onAction
        )
        state.searchQuery.isNotEmpty() && state.searchResults.isEmpty() -> ErrorIndicator(
            title = stringResource(R.string.error_title),
            text = stringResource(R.string.error_text),
            modifier = Modifier.fillMaxSize()
        )
        photos != null -> {
            when {
                photos.loadState.refresh == LoadState.Loading
                    -> LoadingIndicator(modifier = Modifier.fillMaxSize())
                photos.loadState.refresh is LoadState.Error -> ErrorIndicator(
                    title = stringResource(R.string.error_title),
                    text = stringResource(R.string.error_text),
                    modifier = Modifier.fillMaxSize()
                )
                photos.itemCount > 0 ->
                    PhotosGrid(type = PhotosType.ALL, photos = photos, onAction = onAction)
                else -> ErrorIndicator(
                    title = stringResource(R.string.error_title),
                    text = stringResource(R.string.error_text),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        else -> LoadingIndicator(modifier = Modifier.fillMaxSize())
    }
}

enum class PhotosType {
    ALL,
    SEARCH_RESULTS
}

@Composable
private fun PhotosGrid(
    type: PhotosType,
    photos: LazyPagingItems<Photo>? = null,
    filteredPhotos: List<Photo>?  = null,
    onAction: (HomeAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            when (type) {
                PhotosType.SEARCH_RESULTS -> {
                    filteredPhotos?.let { photos ->
                        items(photos.size) { index ->
                            PhotoItem(photos[index], onAction)
                        }
                    }
                }
                PhotosType.ALL -> {
                    photos?.let { photoItems ->
                        items(photoItems.itemCount) { index ->
                            val photo = photoItems[index]
                            photo?.let { PhotoItem(it, onAction) }
                        }
                        item(span = { GridItemSpan(2) }) {
                            if (photos.loadState.append == LoadState.Loading) {
                                LoadingIndicator(Modifier.fillMaxWidth().height(40.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoItem(photo: Photo, onAction: (HomeAction) -> Unit) {
    Box(
        modifier = Modifier
            .size(156.dp)
            .clip(CustomTheme.shapes.photoBig)
            .background(CustomTheme.colors.grayLight)
            .clickable(onClick = {
                onAction(HomeAction.ViewPhoto(id = photo.id))
            })
    ) {
        when (val imageState = photo.imageState) {
            is UiState.Success -> Image(
                bitmap = imageState.data,
                contentDescription = stringResource(
                    R.string.fetched_photo_description
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            is UiState.Loading -> LoadingIndicator(
                modifier = Modifier.fillMaxSize(),
                isIndicatorOnly = true
            )
            is UiState.Error -> ErrorPlaceholder()
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
            contentDescription = null,
            tint = CustomTheme.colors.graySecondary
        )
    }
}