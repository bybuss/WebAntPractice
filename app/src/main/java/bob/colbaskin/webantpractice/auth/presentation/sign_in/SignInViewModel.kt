package bob.colbaskin.webantpractice.auth.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    var state by mutableStateOf(SignInState())

    fun onAction(action: SignInAction) {
        when (action) {
            SignInAction.SignIn -> login()
            is SignInAction.UpdateEmail -> updateEmail(action.email)
            is SignInAction.UpdatePassword -> updatePassword(action.password)
            else -> Unit
        }
    }

    private fun login() {
        state = state.copy(isLoading = true)
        viewModelScope.launch { 
            authRepository.login(
                username = state.email,
                password = state.password,
                clientId = "",
                clientSecret = ""
            )
        }
        state = state.copy(isLoading = false)
    }
    
    private fun updateEmail(email: String) {
        state = state.copy(email = email)
    }
    
    private fun updatePassword(password: String) {
        state = state.copy(password = password)
    }
}