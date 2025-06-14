package bob.colbaskin.webantpractice.home.presentation.home

sealed interface HomeAction {
    data class TabSelected(val index: Int): HomeAction
    data class ViewPhoto(val id: Int): HomeAction
}
