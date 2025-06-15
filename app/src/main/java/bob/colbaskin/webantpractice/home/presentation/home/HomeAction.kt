package bob.colbaskin.webantpractice.home.presentation.home

import bob.colbaskin.webantpractice.home.domain.models.Photo

sealed interface HomeAction {
    data class TabSelected(val index: Int): HomeAction
    data class ViewPhoto(val id: Int): HomeAction
    data class SearchQueryChanged(val query: String): HomeAction
    data class UpdateSearchResults(val photos: List<Photo>): HomeAction
}
