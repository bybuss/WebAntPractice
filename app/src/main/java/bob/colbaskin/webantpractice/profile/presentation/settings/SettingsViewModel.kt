package bob.colbaskin.webantpractice.profile.presentation.settings

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.auth.domain.auth.AuthRepository
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosRepository
import bob.colbaskin.webantpractice.common.takeIfSuccess
import bob.colbaskin.webantpractice.common.toUiState
import bob.colbaskin.webantpractice.common.updateIfSuccess
import bob.colbaskin.webantpractice.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.webantpractice.common.user_prefs.domain.UserPreferencesRepository
import bob.colbaskin.webantpractice.common.utils.toImageBitmap
import bob.colbaskin.webantpractice.di.token.TokenManager
import bob.colbaskin.webantpractice.profile.presentation.common.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val photosRepository: PhotosRepository,
    private val tokenManager: TokenManager,
    private val userPreferences: UserPreferencesRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    init {
        getUserData()
    }

    fun onAction(action: SettingsAction) {
        when(action) {
            is SettingsAction.UpdateUsername -> updateUsername(action.username)
            is SettingsAction.UpdateBirthday -> updateBirthday(action.birthday)
            is SettingsAction.UpdatePhone -> updatePhone(action.phone)
            is SettingsAction.UpdateEmail -> updateEmail(action.email)
            SettingsAction.SignOut -> signOut()
            is SettingsAction.SelectFileFromGallery -> setSelectedImage(action.uri)
            SettingsAction.Save -> saveUserData()
            else -> Unit
        }
    }

    private fun getUserData() {
        state = state.copy(user = UiState.Loading, isUserUpdated = false)

        viewModelScope.launch {
            val response = authRepository.getCurrentUser().toUiState()
            when (response) {
                is UiState.Success -> {
                    state = state.copy(user = UiState.Success(response.data))
                        .also {
                            // TODO: GETTING USER AVATAR 👇🏿 (api doesn't support this yet)
                            // getImage(response.data.avatarPath)
                        }
                }
                is UiState.Error -> state = state.copy(user = UiState.Error(
                    title = context.getString(R.string.error_get_user_title),
                    text = context.getString(R.string.error_get_user_text)
                ))
                else -> {}
            }
        }
    }

    private fun getImage(path: String) {
        state = state.copy(user = UiState.Loading)
        viewModelScope.launch {
            val response = photosRepository.getFile(path = path)
            val imageBitMap = response.toImageBitmap(context)
            state = state.copy(user = state.user.updateIfSuccess {
                copy(userProfilePhoto = Uri.EMPTY)  // FIXME: REPLACE PAINTER TO IMAGE BITMAP FROM API
            })
        }
    }

    private fun setSelectedImage(uri: Uri?) {
        state = state.copy(user = state.user.updateIfSuccess {
            copy(userProfilePhoto = uri)
        })
    }

    private fun signOut() {
        viewModelScope.launch {
            tokenManager.cleatTokens()
            userPreferences.saveAuthStatus(AuthConfig.NOT_AUTHENTICATED)
        }
    }

    private fun saveUserData() {
        val user = state.user.takeIfSuccess() ?: return
        state = state.copy(user = UiState.Loading, isUserUpdated = false)
        viewModelScope.launch {
            val response = authRepository.updateUser(
                id = user.id,
                email = user.email,
                birthday = user.birthday,
                displayName = user.displayName,
                phone = user.phone
            ).toUiState()
            state = state.copy(user = response, isUserUpdated = response is UiState.Success)
        }
    }

    private fun updateUsername(username: String) {
        state = state.copy(user = state.user.updateIfSuccess { copy(displayName = username) })
    }

    private fun updateBirthday(birthday: Long?) {
        state = state.copy(user = state.user.updateIfSuccess { copy(birthday = birthday ?: 0L) })
    }

    private fun updatePhone(phone: String) {
        state = state.copy(user = state.user.updateIfSuccess { copy(phone = phone) })
    }

    private fun updateEmail(email: String) {
        state = state.copy(user = state.user.updateIfSuccess { copy(email = email) })
    }
}