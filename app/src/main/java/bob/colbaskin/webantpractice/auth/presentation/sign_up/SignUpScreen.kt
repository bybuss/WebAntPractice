package bob.colbaskin.webantpractice.auth.presentation.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.CustomTextButton
import bob.colbaskin.webantpractice.common.design_system.CustomTextField
import bob.colbaskin.webantpractice.common.design_system.FilledButton
import bob.colbaskin.webantpractice.common.design_system.TextFieldType
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.navigation.Graphs
import bob.colbaskin.webantpractice.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun SignUpScreenRoot(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val authState = state.authState
    val scope = rememberCoroutineScope()

    SignUpScreen(
        state = state,
        onAction = { action ->
            when (action) {
                SignUpAction.SignUp -> {
                    when (authState) {
                        is UiState.Success -> { navController.navigate(Graphs.Main) }
                        is UiState.Error -> {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    authState.title,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                        else -> {}
                    }
                }
                SignUpAction.SignIn -> { navController.navigate(Screens.SignIn) }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun SignUpScreen(
    state: SignUpState,
    onAction: (SignUpAction) -> Unit,
) {
    val lineColor = CustomTheme.colors.main
    val scrollState = rememberScrollState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                style = CustomTheme.typography.h1,
                modifier = Modifier.drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth + 16
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
            )
            Column {
                CustomTextField(
                    text = state.username,
                    type = TextFieldType.UserName,
                    onValueChange = { newValue ->
                        onAction(SignUpAction.UpdateUsername(newValue))
                    },
                    isRequired = true
                )
                CustomTextField(
                    selectedDate = state.birthday,
                    type = TextFieldType.Birthday,
                    onDateSelected = { newValue ->
                        onAction(SignUpAction.UpdateBirthday(newValue))
                    },
                    isRequired = true
                )
                CustomTextField(
                    text = state.phone,
                    type = TextFieldType.PhoneNumber,
                    onValueChange = { newValue ->
                        onAction(SignUpAction.UpdatePhone(newValue))
                    },
                    isRequired = true
                )
                CustomTextField(
                    text = state.email,
                    type = TextFieldType.Email,
                    onValueChange = { newValue ->
                        onAction(SignUpAction.UpdateEmail(newValue))
                    },
                    isRequired = true,
                    isError = state.isEmailValid
                )
                CustomTextField(
                    text = state.password,
                    type = TextFieldType.Password,
                    onValueChange = { newValue ->
                        onAction(SignUpAction.UpdatePassword(newValue))
                    },
                    isRequired = true,
                    isError = state.isPasswordEquals
                )
                CustomTextField(
                    text = state.confirmPassword,
                    type = TextFieldType.ConfirmPassword,
                    onValueChange = { newValue ->
                        onAction(SignUpAction.UpdateConfirmPassword(newValue))
                    },
                    isRequired = true,
                    isError = state.isPasswordEquals
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                FilledButton(
                    text = stringResource(R.string.sign_up),
                    onClick = { onAction(SignUpAction.SignUp) },
                    isLoading = state.isLoading,
                    modifier = Modifier.width(163.dp)
                )
                CustomTextButton(
                    text = stringResource(R.string.sign_in),
                    onClick = { onAction(SignUpAction.SignIn) },
                    modifier = Modifier.width(163.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpScreen() {
    val viewmodel = hiltViewModel<SignUpViewModel>()
    WebAntPracticeTheme {
        SignUpScreen(
            state = viewmodel.state,
            onAction = viewmodel::onAction
        )
    }
}