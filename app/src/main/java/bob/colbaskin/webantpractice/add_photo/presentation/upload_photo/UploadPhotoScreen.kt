package bob.colbaskin.webantpractice.add_photo.presentation.upload_photo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.FABButton
import bob.colbaskin.webantpractice.common.design_system.LoadingIndicator
import bob.colbaskin.webantpractice.common.design_system.TextWithActionTopAppBar
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.navigation.Screens
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun UploadPhotoScreenRoot(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: UploadPhotoViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.onAction(UploadPhotoAction.SelectFileFromGallery(it))
        }
    }

    LaunchedEffect(state.navigationEvent) {
        state.navigationEvent?.let { event ->
            navController.navigate(
                Screens.AddPhotoData(
                    fileId = event.fileId,
                    imageUri = event.imageUri.toString()
                )
            )
            viewModel.onAction(UploadPhotoAction.ResetNavigation)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onAction(UploadPhotoAction.ResetNavigation)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            TextWithActionTopAppBar(
                title = stringResource(R.string.top_bar_title_all_photos),
                actionButtonLabel = stringResource(R.string.next),
                onActionClick = {
                    if (state.selectedImage != null) {
                        viewModel.onAction(UploadPhotoAction.UploadFile)
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                context.getString(R.string.toast_empty_selected_image)
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FABButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.size(64.dp).offset(y = (-30).dp, x = (-30).dp)
            )
        },
        contentWindowInsets = WindowInsets(0),
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        UploadPhotoScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun UploadPhotoScreen(
    state: UploadPhotoState,
    onAction: (UploadPhotoAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    val transformState = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
    }

    LaunchedEffect(transformState.isTransformInProgress) {
        if (!transformState.isTransformInProgress && scale > 1f || scale < 1f) scale = 1f
    }

    when {
        state.isLoading == true -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize().background(CustomTheme.colors.white))
        }
        else -> {
            Column(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .background(CustomTheme.colors.grayLight),
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(CustomTheme.colors.grayLight)
                            .height(240.dp)
                            .zIndex(100f),
                        contentAlignment = Alignment.Center
                    ) {
                        state.selectedImage?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = stringResource(R.string.fetched_photo_description),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer(scaleX = scale, scaleY = scale)
                                    .transformable(state = transformState)
                            )
                        } ?: Text(
                            text = stringResource(R.string.empty_selected_photo),
                            style = CustomTheme.typography.h3,
                            color = CustomTheme.colors.gray
                        )
                    }
                }
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                    Text(
                        text = stringResource(R.string.select_photo),
                        style = CustomTheme.typography.p,
                        color = CustomTheme.colors.black
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        repeat(100) {
                            item {
                                PhotoItem(onAction)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoItem(onAction: (UploadPhotoAction) -> Unit) {
    val uri = Uri.EMPTY
    Box(
        modifier = Modifier
            .size(78.dp)
            .clip(CustomTheme.shapes.photoSmall)
            .background(CustomTheme.colors.grayLight)
            .clickable(onClick = {
                onAction(UploadPhotoAction.SelectFileFromGallery(uri))
            })
    ) {
        Image(
            painter = painterResource(R.drawable.webant_logo),
            contentDescription = stringResource(R.string.fetched_photo_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
