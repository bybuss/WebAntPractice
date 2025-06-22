package bob.colbaskin.webantpractice.profile.presentation.common

import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User

data class ProfileState(
    val user: UiState<User> = UiState.Loading,
    val isUserUpdated: Boolean = false,
)