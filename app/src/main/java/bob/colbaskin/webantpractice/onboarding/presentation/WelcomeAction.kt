package bob.colbaskin.webantpractice.onboarding.presentation

sealed interface WelcomeAction {
    data object CreateAccount: WelcomeAction
    data object HaveAccount: WelcomeAction
}
