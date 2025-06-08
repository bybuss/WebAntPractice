package bob.colbaskin.webantpractice.auth.presentation.sign_up

sealed interface SignUpAction {
    data object SignUp : SignUpAction
    data object SignIn : SignUpAction
    data class UpdateUsername(val username: String) : SignUpAction
    data class UpdateBirthday(val birthday: Long?) : SignUpAction
    data class UpdatePhone(val phone: String) : SignUpAction
    data class UpdateEmail(val email: String) : SignUpAction
    data class UpdatePassword(val password: String) : SignUpAction
    data class UpdateConfirmPassword(val confirmPassword: String) : SignUpAction
}