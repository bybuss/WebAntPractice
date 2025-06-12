package bob.colbaskin.webantpractice.auth.presentation.sign_up

import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User

data class SignUpState(
    val username: String = "",
    val birthday: Long? = null,
    val phone: String = "",
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordEquals: Boolean = false,
    val isLoading: Boolean = false,
    val authState: UiState<User> = UiState.Loading
)
