package bob.colbaskin.webantpractice.profile.presentation.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.auth.domain.auth.AuthRepository
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosRepository
import bob.colbaskin.webantpractice.common.toUiState
import bob.colbaskin.webantpractice.common.utils.toImageBitmap
import bob.colbaskin.webantpractice.profile.presentation.common.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
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
            val response = authRepository.getCurrentUser().toUiState()
            when (response) {
                is UiState.Success -> {
                    val userData = response.data
                    state = state.copy(user = UiState.Success(userData))
                        .also {
                                // TODO: GETTING USER AVATAR 👇🏿 (api doesn't support this yet)
                                // getImage(response.data.avatarPath)
                        }
                }
                else -> {}
            }
            state = state.copy(user = response)
        }
    }

    private fun getImage(path: String) {
        state = state.copy(user = UiState.Loading)
        viewModelScope.launch {
            val response = photosRepository.getFile(path = path)
            val imageBitMap = response.toImageBitmap(context)
        }
    }
}