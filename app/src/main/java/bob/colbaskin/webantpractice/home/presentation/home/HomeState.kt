package bob.colbaskin.webantpractice.home.presentation.home

import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.home.domain.models.Photo

data class HomeState(
    val selectedIndex: Int = 0,
    val photos: UiState<List<Photo>> = UiState.Loading
)
