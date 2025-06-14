package bob.colbaskin.webantpractice.home.presentation.viewing_photo

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.BackWithMenuTopAppBar
import bob.colbaskin.webantpractice.common.design_system.ErrorIndicator
import bob.colbaskin.webantpractice.common.design_system.LoadingIndicator
import bob.colbaskin.webantpractice.common.design_system.MenuItem
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.utils.toFormattedDate
import bob.colbaskin.webantpractice.navigation.Screens

@Composable
fun ViewingPhotoScreenRoot(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: ViewingPhotoViewModel = hiltViewModel()
) {
    val photoId: Int?
        = navController.currentBackStackEntry?.arguments?.getInt("id")
    val context: Context = LocalContext.current
    LaunchedEffect (true) {
        if (photoId != null) {
            viewModel.onAction(ViewingPhotoAction.GetPhotoById(photoId))
        } else {
            snackbarHostState.showSnackbar(context.getString(R.string.null_error_title))
        }
    }

    Scaffold (
        topBar = {
            BackWithMenuTopAppBar(
                onBackClick = { navController.popBackStack() },
                menuItems = listOf(
                    MenuItem(
                        stringResource(R.string.dropdown_edit_item),
                        action = {
                            navController.navigate(Screens.EditingPhoto(id = photoId))
                        }
                    )
                ),
                menuModifier = Modifier.width(200.dp)
            )
        },
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        ViewingPhotoScreen(
            state = viewModel.state,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ViewingPhotoScreen(
    state: ViewingPhotoState,
    modifier: Modifier = Modifier
) {
    val photoState = state.fullPhoto

    when (photoState) {
        UiState.Loading -> LoadingIndicator(modifier = modifier.fillMaxSize())
        is UiState.Error -> ErrorIndicator(
            title = photoState.title,
            text = photoState.text,
            modifier = modifier.fillMaxSize()
        )
        is UiState.Success -> {
            PhotoViewing(
                state = state,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun PhotoViewing(
    state: ViewingPhotoState,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    val transformState = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
    }
    val fullPhotoState = (state.fullPhoto as UiState.Success).data

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Box(
                modifier = Modifier
                    .graphicsLayer(scaleX = scale, scaleY = scale)
                    .transformable(state = transformState)
                    .background(CustomTheme.colors.gray)
                    .height(360.dp),
                contentAlignment = Alignment.Center
            ) {
                val imageState = fullPhotoState.imageState
                when (imageState) {
                    is UiState.Success -> {
                        Image(
                            bitmap = imageState.data,
                            contentDescription = stringResource(R.string.fetched_photo_description),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> LoadingIndicator(
                        isIndicatorOnly = true,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = fullPhotoState.name,
                    style = CustomTheme.typography.h2,
                    color = CustomTheme.colors.black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = fullPhotoState.user.displayName,
                        style = CustomTheme.typography.p,
                        color = CustomTheme.colors.gray
                    )
                    Text(
                        text = fullPhotoState.dateCreate.toFormattedDate(),
                        style = CustomTheme.typography.caption,
                        color = CustomTheme.colors.gray
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = fullPhotoState.description,
                    style = CustomTheme.typography.p,
                    color = CustomTheme.colors.black
                )
            }
        }
    }
}
