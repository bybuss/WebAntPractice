package bob.colbaskin.webantpractice.profile.presentation.change_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(

): ViewModel() {
    var state by mutableStateOf(ChangePasswordState())
        private set

    fun onAction(action: ChangePasswordAction) {
        when(action) {
            is ChangePasswordAction.UpdateOldPassword -> updateOldPassword(action.oldPassword)
            is ChangePasswordAction.UpdateNewPassword -> updateNewPassword(action.newPassword)
            is ChangePasswordAction.UpdateConfirmPassword -> updateConfirmPassword(action.confirmPassword)
            else -> Unit
        }
    }

    private fun updateOldPassword(oldPassword: String) {
        state = state.copy(oldPassword = oldPassword)
    }

    private fun updateNewPassword(newPassword: String) {
        state = state.copy(newPassword = newPassword)
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        state = state.copy(confirmPassword = confirmPassword)
    }
}