package bob.colbaskin.webantpractice.auth.presentation.sign_in

import bob.colbaskin.webantpractice.common.UiState

data class SignInState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isLoading: Boolean = false,
    val authState: UiState<Unit> = UiState.Loading
)
