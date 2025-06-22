package bob.colbaskin.webantpractice.profile.presentation.change_password

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.auth.domain.auth.AuthRepository
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
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

    fun changePassword() {
        state = state.copy(isLoading = true, isPasswordChanged = false, errorMessage = null)
        viewModelScope.launch {
            val userResult = authRepository.getCurrentUser().toUiState()
            val user = (userResult as? UiState.Success)?.data
            if (user != null) {
                val response = authRepository.changePassword(
                    id = user.id,
                    oldPassword = state.oldPassword,
                    newPassword = state.newPassword
                ).toUiState()
                when (response) {
                    is UiState.Error -> state = state.copy(errorMessage = response.title)
                    is UiState.Success -> state = state.copy(isPasswordChanged = true)
                    else -> {}
                }
            }
            state = state.copy(isLoading = false)
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