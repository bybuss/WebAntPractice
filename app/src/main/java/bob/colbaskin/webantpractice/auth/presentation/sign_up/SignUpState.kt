package bob.colbaskin.webantpractice.auth.presentation.sign_up

data class SignUpState(
    val username: String = "",
    val birthday: Long? = null,
    val phone: String = "",
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordEquals: Boolean = false,
    val isLoading: Boolean = false
)
