package bob.colbaskin.webantpractice.profile.presentation.change_password

data class ChangePasswordState(
    val oldPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
) {
    val isConfirmEnabled: Boolean
        get() = oldPassword.isNotEmpty() && newPassword.isNotEmpty()
                && confirmPassword.isNotEmpty() && newPassword == confirmPassword
}
