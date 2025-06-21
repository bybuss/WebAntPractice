package bob.colbaskin.webantpractice.profile.presentation.settings

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.BackTextTopAppBar
import bob.colbaskin.webantpractice.common.design_system.CustomTextButton
import bob.colbaskin.webantpractice.common.design_system.CustomTextField
import bob.colbaskin.webantpractice.common.design_system.Dialog
import bob.colbaskin.webantpractice.common.design_system.ErrorIndicator
import bob.colbaskin.webantpractice.common.design_system.FilledButton
import bob.colbaskin.webantpractice.common.design_system.LoadingIndicator
import bob.colbaskin.webantpractice.common.design_system.TextButtonType
import bob.colbaskin.webantpractice.common.design_system.TextFieldType
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.utils.clickableWithoutRipple
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import bob.colbaskin.webantpractice.navigation.Graphs
import bob.colbaskin.webantpractice.navigation.Screens
import bob.colbaskin.webantpractice.profile.presentation.common.ProfileState
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun SettingsScreenRoot(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showExitDialog by remember { mutableStateOf(false) }
    var showDeletingDialog by remember { mutableStateOf(false) }
    var showAccountExitDialog by remember { mutableStateOf(false) }

    if (showAccountExitDialog) {
        Dialog(
            dialogTitle = stringResource(R.string.dialog_confirmation),
            dialogText = stringResource(R.string.dialog_account_exit),
            onDismissRequest = { showAccountExitDialog = false },
            actionLabel1 = stringResource(R.string.dialog_exit),
            actionLabel2 = stringResource(R.string.dialog_cancel),
            onConfirmation = {
                viewModel.onAction(SettingsAction.SignOut)
                navController.navigate(Graphs.Onboarding) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
                showAccountExitDialog = false
            }
        )
    }

    BackHandler { showExitDialog = true }
    if (showExitDialog) {
        Dialog(
            dialogTitle = stringResource(R.string.dialog_confirmation),
            dialogText = stringResource(R.string.dialog_exit_data_lost),
            onDismissRequest = { showExitDialog = false },
            actionLabel1 = stringResource(R.string.dialog_exit),
            actionLabel2 = stringResource(R.string.dialog_cancel),
            onConfirmation = {
                navController.popBackStack()
                showExitDialog = false
            }
        )
    }
    if (showDeletingDialog) {
        Dialog(
            dialogTitle = stringResource(R.string.dialog_confirmation),
            dialogText = stringResource(R.string.dialog_delete_account),
            onDismissRequest = { showDeletingDialog = false },
            actionLabel1 = stringResource(R.string.dialog_delete),
            actionLabel2 = stringResource(R.string.dialog_cancel),
            onConfirmation = {
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(
                        R.string.toast_mock_account_deleted
                    ))
                }
                showDeletingDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            BackTextTopAppBar(
                title = stringResource(R.string.top_bar_title_settings),
                onBackClick = { showExitDialog = true },
            )
        },
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        SettingsScreen(
            state = viewModel.state,
            onAction = { action ->
                when (action) {
                    is SettingsAction.SelectFileFromGallery -> {
                        scope.launch {
                            snackbarHostState.showSnackbar(context.getString(
                                R.string.toast_photo_successfully
                            ))
                        }
                    }
                    SettingsAction.OnDeleteAccountClick -> showDeletingDialog = true
                    SettingsAction.OnSignOutClick -> showAccountExitDialog = true
                    SettingsAction.ChangePassword -> navController.navigate(Screens.ChangePassword)
                    else -> Unit
                }
                viewModel.onAction(action)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun SettingsScreen(
    state: ProfileState,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val userState = state.user) {
        is UiState.Success -> SettingsContent(
            user = userState.data,
            onAction = onAction,
            modifier = modifier
        )
        is UiState.Error -> {
            ErrorIndicator(
                title = userState.title,
                text = userState.text,
                modifier = modifier.fillMaxSize()
            )
        }
        UiState.Loading -> LoadingIndicator(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun SettingsContent(
    user: User,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onAction(SettingsAction.SelectFileFromGallery(it)) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(CustomTheme.colors.white)
                .border(1.dp, CustomTheme.colors.grayLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (user.userProfilePhoto != null) {
                Image(
                    painter = rememberAsyncImagePainter(user.userProfilePhoto), // FIXME: REPLACE PAINTER TO IMAGE BITMAP FROM API
                    contentDescription = stringResource(R.string.profile_photo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.webant_logo),
                    contentDescription = stringResource(R.string.profile_photo),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 9.dp, horizontal = 10.dp)
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape)
                    .background(CustomTheme.colors.avatarEditBox.copy(alpha = 0.5f))
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
                    .clickableWithoutRipple(onClick = {
                        galleryLauncher.launch("image/*")
                    })
            ) {
                Text(
                    text = "Edit",
                    color = CustomTheme.colors.white,
                    style = CustomTheme.typography.caption,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.personal_data),
                style = CustomTheme.typography.p,
                color = CustomTheme.colors.black,
            )
            Spacer(modifier = Modifier.height(14.dp))
            Column {
                CustomTextField(
                    text = user.displayName,
                    type = TextFieldType.UserName,
                    onValueChange = { newValue ->
                        onAction(SettingsAction.UpdateUsername(newValue))
                    }
                )
                CustomTextField(
                    selectedDate = user.birthday,
                    type = TextFieldType.Birthday,
                    onDateSelected = { newValue ->
                        onAction(SettingsAction.UpdateBirthday(newValue))
                    }
                )
                CustomTextField(
                    text = user.email,
                    type = TextFieldType.Email,
                    onValueChange = { newValue ->
                        onAction(SettingsAction.UpdateEmail(newValue))
                    }
                )
                CustomTextField(
                    text = user.phone,
                    type = TextFieldType.PhoneNumber,
                    onValueChange = { newValue ->
                        onAction(SettingsAction.UpdatePhone(newValue))
                    }
                )
            }
        }
        FilledButton(
            text = stringResource(R.string.save),
            onClick = {
                // TODO: SAVE SETTINGS
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
        )
        CustomTextButton(
            text = stringResource(R.string.reset_password),
            onClick = { onAction(SettingsAction.ChangePassword) },
            type = TextButtonType.Pink,
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row {
                Text(
                    text = stringResource(R.string.you_can_delete_your_account_part1) + " ",
                    style = CustomTheme.typography.p,
                    color = CustomTheme.colors.black
                )
                Text(
                    text = stringResource(R.string.you_can_delete_your_account_part2),
                    style = CustomTheme.typography.p,
                    color = CustomTheme.colors.main,
                    modifier = Modifier.clickableWithoutRipple(onClick = {
                        onAction(SettingsAction.OnDeleteAccountClick)
                    })
                )
            }
            Text(
                text = stringResource(R.string.sign_out),
                style = CustomTheme.typography.p,
                color = CustomTheme.colors.main,
                modifier = Modifier.clickableWithoutRipple(onClick = {
                    onAction(SettingsAction.OnSignOutClick)
                })
            )
        }
    }
}
