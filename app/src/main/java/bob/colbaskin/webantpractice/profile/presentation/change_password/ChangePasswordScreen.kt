package bob.colbaskin.webantpractice.profile.presentation.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.BackTextTopAppBar
import bob.colbaskin.webantpractice.common.design_system.CustomTextField
import bob.colbaskin.webantpractice.common.design_system.FilledButton
import bob.colbaskin.webantpractice.common.design_system.TextFieldType
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.navigation.Graphs
import bob.colbaskin.webantpractice.navigation.Screens

@Composable
fun ChangePasswordScreenRoot(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(state.errorMessage) {
        if (!state.errorMessage.isNullOrEmpty()) {
            snackbarHostState.showSnackbar(state.errorMessage)
        }
    }
    Scaffold(
        topBar = {
            BackTextTopAppBar(
                title = stringResource(R.string.top_bar_title_new_password),
                onBackClick = { navController.popBackStack() },
            )
        },
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        ChangePasswordScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    ChangePasswordAction.NavigateToSuccess -> {
                        viewModel.changePassword()
                    }
                    else -> Unit
                }
                viewModel.onAction(action)
            },
            onSuccess = {
                navController.navigate(Screens.SuccessChangePassword) {
                    popUpTo(Graphs.Detailed) { inclusive = true }
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ChangePasswordScreen(
    state: ChangePasswordState,
    onSuccess: () -> Unit,
    onAction: (ChangePasswordAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(state.isPasswordChanged) {
        if (state.isPasswordChanged) {
            onSuccess()
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.enter_new_password),
            style = CustomTheme.typography.p,
            color = CustomTheme.colors.black
        )
        Spacer(modifier = Modifier.height(14.dp))
        CustomTextField(
            text = state.oldPassword,
            type = TextFieldType.Password,
            placeholderText = stringResource(R.string.placeholder_old_password),
            onValueChange = { onAction(ChangePasswordAction.UpdateOldPassword(it)) },
        )
        CustomTextField(
            text = state.newPassword,
            type = TextFieldType.Password,
            onValueChange = { onAction(ChangePasswordAction.UpdateNewPassword(it)) },
        )
        CustomTextField(
            text = state.confirmPassword,
            type = TextFieldType.Password,
            placeholderText = stringResource(R.string.placeholder_confirm_password),
            onValueChange = { onAction(ChangePasswordAction.UpdateConfirmPassword(it)) },
        )
        Spacer(modifier = Modifier.height(20.dp))
        FilledButton(
            text = stringResource(R.string.confirm_button),
            onClick = { onAction(ChangePasswordAction.NavigateToSuccess) },
            enabled = state.isConfirmEnabled,
            isLoading = state.isLoading,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
