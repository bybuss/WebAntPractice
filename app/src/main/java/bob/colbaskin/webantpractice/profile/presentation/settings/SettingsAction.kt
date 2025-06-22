package bob.colbaskin.webantpractice.profile.presentation.settings

import android.net.Uri

sealed interface SettingsAction {
    data class UpdateUsername(val username: String) : SettingsAction
    data class UpdateBirthday(val birthday: Long?) : SettingsAction
    data class UpdateEmail(val email: String) : SettingsAction
    data class UpdatePhone(val phone: String) : SettingsAction
    data object SignOut: SettingsAction
    data object OnSignOutClick: SettingsAction
    data class SelectFileFromGallery(val uri: Uri): SettingsAction
    data object OnDeleteAccountClick: SettingsAction
    data object ChangePassword: SettingsAction
    data object Save: SettingsAction
}