package bob.colbaskin.webantpractice.common.user_prefs.data.models

data class UserPreferences(
    val onboardingStatus: OnboardingConfig,
    val authStatus: AuthConfig,

    val userId: Int,
    val username: String,
    val birthDateMs: Long,
    val phone: String,
    val email: String,
    val avatarUrl: String?,
)