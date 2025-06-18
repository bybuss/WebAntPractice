package bob.colbaskin.webantpractice.home.presentation.editing_photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.BackTextTopAppBar
import bob.colbaskin.webantpractice.common.design_system.CustomCheckbox
import bob.colbaskin.webantpractice.common.design_system.CustomTextField
import bob.colbaskin.webantpractice.common.design_system.ErrorIndicator
import bob.colbaskin.webantpractice.common.design_system.FilledButton
import bob.colbaskin.webantpractice.common.design_system.LoadingIndicator
import bob.colbaskin.webantpractice.common.design_system.TextFieldType
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme

@Composable
fun EditingPhotoScreenRoot(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: EditingPhotoViewModel = hiltViewModel()
) {
    val photoId: Int?
        = navController.currentBackStackEntry?.arguments?.getInt("id")
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(state.fullPhoto) {
        if (state.fullPhoto is UiState.Error) {
            snackbarHostState.showSnackbar(state.fullPhoto.title)
        }
    }
    LaunchedEffect (true) {
        if (photoId != null) {
            viewModel.onAction(EditingPhotoAction.GetPhotoById(photoId))
        } else {
            snackbarHostState.showSnackbar(context.getString(R.string.null_error_title))
        }
    }

    Scaffold (
        topBar = {
            BackTextTopAppBar(
                title = stringResource(R.string.top_bar_title_edit_photo),
                onBackClick = { navController.popBackStack() },
            )
        },
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        EditingPhotoScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    else -> Unit
                }
                viewModel.onAction(action)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun EditingPhotoScreen(
    state: EditingPhotoState,
    onAction: (EditingPhotoAction) -> Unit,
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
        is UiState.Success -> PhotoEditingContent(state, onAction, modifier)
    }
}

@Composable
private fun PhotoEditingContent(
    state: EditingPhotoState,
    onAction: (EditingPhotoAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    val transformState = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
    }
    val fullPhotoState = (state.fullPhoto as UiState.Success).data

    LaunchedEffect(transformState.isTransformInProgress) {
        if (!transformState.isTransformInProgress && scale > 1f || scale < 1f) scale = 1f
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CustomTheme.colors.grayLight)
                    .zIndex(100f),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .background(CustomTheme.colors.grayLight)
                        .height(240.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when (val imageState = fullPhotoState.imageState) {
                        is UiState.Success -> {
                            Image(
                                bitmap = imageState.data,
                                contentDescription = stringResource(R.string.fetched_photo_description),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer(scaleX = scale, scaleY = scale)
                                    .transformable(state = transformState)
                            )
                        }
                        else -> LoadingIndicator(
                            isIndicatorOnly = true,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                CustomTextField(
                    text = fullPhotoState.name,
                    type = TextFieldType.Empty,
                    onValueChange = {
                        onAction(EditingPhotoAction.UpdateImageName(it))
                    },
                    placeholderText = stringResource(R.string.placeholder_name)
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextField(
                    text = fullPhotoState.description,
                    type = TextFieldType.Empty,
                    onValueChange = {
                        onAction(EditingPhotoAction.UpdateImageDescription(it))
                    },
                    placeholderText = stringResource(R.string.placeholder_description),
                    modifier = Modifier.defaultMinSize(minHeight = 128.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    CustomCheckbox(
                        text = stringResource(R.string.checkbox_new),
                        checked = fullPhotoState.new,
                        onCheckedChange = {
                            onAction(EditingPhotoAction.UpdateImageIsNew(it))
                        }
                    )
                    CustomCheckbox(
                        text = stringResource(R.string.checkbox_popular),
                        checked = fullPhotoState.popular,
                        onCheckedChange = {
                            onAction(EditingPhotoAction.UpdateImageIsPopular(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                FilledButton(
                    text = stringResource(R.string.confirm_button),
                    onClick = {
                        onAction(EditingPhotoAction.UpdatePhoto(
                            id = fullPhotoState.id,
                            fileId = fullPhotoState.file.id,
                            userId = fullPhotoState.user.id,
                            description = fullPhotoState.description,
                            name = fullPhotoState.name,
                            isNew = fullPhotoState.new,
                            isPopular = fullPhotoState.popular
                        ))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
