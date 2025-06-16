package bob.colbaskin.webantpractice.common.editing_photo

import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.home.domain.models.FullPhoto

data class EditingPhotoState(
    val fullPhoto: UiState<FullPhoto> = UiState.Loading
)
