package bob.colbaskin.webantpractice.auth.presentation.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.auth.domain.auth.AuthRepository
import bob.colbaskin.webantpractice.common.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "Auth"

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    fun onAction(action: SignUpAction) {
        when (action) {
            SignUpAction.SignUp -> register()
            is SignUpAction.UpdateUsername -> updateUsername(action.username)
            is SignUpAction.UpdateBirthday -> updateBirthday(action.birthday)
            is SignUpAction.UpdatePhone -> updatePhone(action.phone)
            is SignUpAction.UpdateEmail -> updateEmail(action.email)
            is SignUpAction.UpdatePassword -> updatePassword(action.password)
            is SignUpAction.UpdateConfirmPassword -> updateConfirmPassword(action.confirmPassword)
            else -> Unit
        }
    }

    private fun register() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val response = authRepository.register(
                email = state.email,
                birthday = state.birthday ?: 0L,
                displayName = state.username,
                phone = state.phone,
                plainPassword = state.password
            ).toUiState()

            state = state.copy(
                authState = response,
                isLoading = false
            )
        }
    }

    private fun updateUsername(username: String) {
        state = state.copy(username = username)
    }

    private fun updateBirthday(birthday: Long?) {
        state = state.copy(birthday = birthday ?: 0L)
    }

    private fun updatePhone(phone: String) {
        state = state.copy(phone = phone)
    }

    private fun updateEmail(email: String) {
        state = state.copy(email = email)
    }

    private fun updatePassword(password: String) {
        state = state.copy(password = password)
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        state = state.copy(confirmPassword = confirmPassword)
    }
}