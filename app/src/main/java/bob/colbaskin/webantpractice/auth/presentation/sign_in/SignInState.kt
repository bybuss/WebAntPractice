package bob.colbaskin.webantpractice.auth.presentation.sign_in

data class SignInState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isLoading: Boolean = false
)
