package bob.colbaskin.webantpractice.profile.presentation.profile

import android.content.Context
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
import bob.colbaskin.webantpractice.common.utils.toImageBitmap
import bob.colbaskin.webantpractice.profile.domain.ProfileRepository
import bob.colbaskin.webantpractice.profile.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val photosRepository: PhotosRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    init { getProfile() }

    fun onAction(action: ProfileAction) {
        when(action) {
            else -> Unit
        }
    }

    private fun getProfile() {
        state = state.copy(user = UiState.Loading)
        viewModelScope.launch {
            val userId : Int? = authRepository.getCurrentUser().toUiState().takeIfSuccess()?.id
            val user: UiState<User> = if (userId != null) {
                val response = profileRepository.getUserById(userId).toUiState()
                when (response) {
                    is UiState.Success -> response.data.also {
                        // TODO: GETTING USER AVATAR 👇🏿 (api doesn't support this yet)
                        // getImage(response.data.avatarPath)
                    }
                    else -> {}
                }
                response
            } else UiState.Error(
                title = context.getString(R.string.error_get_user_title),
                text = context.getString(R.string.error_get_user_text)
            )
            state = state.copy(user = user)
        }
    }

    private fun getImage(path: String) {
        state = state.copy(UiState.Loading)
        viewModelScope.launch {
            val response = photosRepository.getFile(path = path)
            val imageBitMap = response.toImageBitmap(context)
            state = state.copy(image = imageBitMap)
        }
    }
}