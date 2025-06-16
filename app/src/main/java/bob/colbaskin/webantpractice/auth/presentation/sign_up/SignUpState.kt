package bob.colbaskin.webantpractice.auth.presentation.sign_up

import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User

data class SignUpState(
    val username: String = "",
    val birthday: Long? = null,
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val authState: UiState<User> = UiState.Loading
) {
    val isEmailValid: Boolean
        get() = email.isNotEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordEquals: Boolean
        get() = password == confirmPassword
}
