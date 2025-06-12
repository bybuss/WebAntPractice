package bob.colbaskin.webantpractice.navigation

import kotlinx.serialization.Serializable

interface Graphs {

    @Serializable
    data object Onboarding : Graphs

    @Serializable
    data object Main : Graphs

    @Serializable
    data object Detailed : Graphs
}
