package bob.colbaskin.webantpractice.profile.presentation.change_password

sealed interface ChangePasswordAction {
    data class UpdateOldPassword(val oldPassword: String): ChangePasswordAction
    data class UpdateNewPassword(val newPassword: String): ChangePasswordAction
    data class UpdateConfirmPassword(val confirmPassword: String): ChangePasswordAction
    data object NavigateToSuccess: ChangePasswordAction
    data object ReturnToProfile: ChangePasswordAction
}