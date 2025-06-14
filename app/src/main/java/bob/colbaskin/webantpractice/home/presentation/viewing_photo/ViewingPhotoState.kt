package bob.colbaskin.webantpractice.home.presentation.viewing_photo

import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.home.domain.models.FullPhoto

data class ViewingPhotoState(
    val fullPhoto: UiState<FullPhoto> = UiState.Loading
)
