package bob.colbaskin.webantpractice.profile.presentation.profile

import androidx.compose.ui.graphics.ImageBitmap
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.profile.domain.models.User

data class ProfileState(
    val user: UiState<User> = UiState.Loading,
    val image: UiState<ImageBitmap> = UiState.Loading // FIXME: MOST LIKELY THE IMAGE PARAM WILL BE IN THE USER
)
