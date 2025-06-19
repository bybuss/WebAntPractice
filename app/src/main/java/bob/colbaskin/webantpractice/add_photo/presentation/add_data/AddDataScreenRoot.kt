package bob.colbaskin.webantpractice.add_photo.presentation.add_data

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.BackTextTopAppBar
import bob.colbaskin.webantpractice.common.design_system.CustomCheckbox
import bob.colbaskin.webantpractice.common.design_system.CustomTextField
import bob.colbaskin.webantpractice.common.design_system.Dialog
import bob.colbaskin.webantpractice.common.design_system.FilledButton
import bob.colbaskin.webantpractice.common.design_system.TextFieldType
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.navigation.Screens
import coil.compose.rememberAsyncImagePainter

@Composable
fun AddDataScreenRoot(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: AddDataViewModel = hiltViewModel()
) {
    val fileId: Int?
            = navController.currentBackStackEntry?.arguments?.getInt("fileId")
    val imageUri: Uri
        = navController.currentBackStackEntry?.arguments?.getString("imageUri")?.toUri() ?: Uri.EMPTY
    val state = viewModel.state
    val context = LocalContext.current
    var showWarningDialog by remember { mutableStateOf(false) }

    LaunchedEffect(imageUri) {
        viewModel.onAction(AddDataAction.UpdateUri(imageUri))
    }
    LaunchedEffect (fileId) {
        if (fileId == null) {
            snackbarHostState.showSnackbar(context.getString(R.string.null_error_title))
            navController.popBackStack()
        }
    }
    LaunchedEffect(state.fullPhoto) {
        when (state.fullPhoto) {
            is UiState.Error -> snackbarHostState.showSnackbar(state.fullPhoto.title)
            is UiState.Success -> {
                navController.navigate(Screens.Home)
                snackbarHostState.showSnackbar(context.getString(
                    R.string.toast_photo_successfully)
                )
            }
            else -> {}
        }
    }

    BackHandler { showWarningDialog = true }
    if (showWarningDialog) {
        Dialog(
            dialogTitle = stringResource(R.string.dialog_confirmation),
            dialogText = stringResource(R.string.dialog_exit_data_lost),
            onDismissRequest = { showWarningDialog = false },
            actionLabel1 = stringResource(R.string.dialog_exit),
            actionLabel2 = stringResource(R.string.dialog_cancel),
            onConfirmation = {
                navController.popBackStack()
                showWarningDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            BackTextTopAppBar(
                title = stringResource(R.string.top_bar_title_new_photo),
                onBackClick = { showWarningDialog = true },
            )
        },
        contentWindowInsets = WindowInsets(0),
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        AddDataScreen(
            state = state,
            fileId = fileId!!,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun AddDataScreen(
    state: AddDataState,
    fileId: Int,
    onAction: (AddDataAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    val transformState = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
    }

    LaunchedEffect(transformState.isTransformInProgress) {
        if (!transformState.isTransformInProgress && scale > 1f || scale < 1f) scale = 1f
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Box(
                modifier = Modifier
                    .background(CustomTheme.colors.grayLight)
                    .size(360.dp)
                    .zIndex(100f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(state.image),
                    contentDescription = stringResource(R.string.fetched_photo_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 4.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                        .transformable(state = transformState)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                CustomTextField(
                    text = state.name,
                    type = TextFieldType.Empty,
                    onValueChange = {
                        onAction(AddDataAction.UpdateImageName(it))
                    },
                    placeholderText = stringResource(R.string.placeholder_name),
                    isRequired = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextField(
                    text = state.description,
                    type = TextFieldType.Empty,
                    onValueChange = {
                        onAction(AddDataAction.UpdateImageDescription(it))
                    },
                    placeholderText = stringResource(R.string.placeholder_description),
                    isRequired = true,
                    modifier = Modifier.defaultMinSize(minHeight = 128.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    CustomCheckbox(
                        text = stringResource(R.string.checkbox_new),
                        checked = state.isNew,
                        onCheckedChange = {
                            onAction(AddDataAction.UpdateImageIsNew(it))
                        }
                    )
                    CustomCheckbox(
                        text = stringResource(R.string.checkbox_popular),
                        checked = state.isPopular,
                        onCheckedChange = {
                            onAction(AddDataAction.UpdateImageIsPopular(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                FilledButton(
                    text = stringResource(R.string.confirm_button),
                    onClick = {
                        onAction(AddDataAction.AddNewPhoto(fileId = fileId))
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
                    isLoading = state.isLoading,
                    enabled = state.isConfirmButtonEnabled
                )
            }
        }
    }
}
