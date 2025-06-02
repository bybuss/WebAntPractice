package bob.colbaskin.webantpractice.common.user.models

data class UserPreferences(
    val onBoardingStatus: OnBoardingConfig,
    val authStatus: AuthConfig,

    val userId: Int,
    val username: String,
    val birthDateMs: Long,
    val phone: String,
    val email: String,
    val avatarUrl: String,
)

fun UserPreferences.toDomain(): User {
    return User(
        id = this.userId,
        email = this.email,
        userProfilePhoto = this.avatarUrl,
        birthday = this.birthDateMs,
        displayName = this.username,
        phone = this.phone
    )
}
