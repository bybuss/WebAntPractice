package bob.colbaskin.webantpractice.common.user

import bob.colbaskin.webantpractice.common.user.models.AuthConfig
import bob.colbaskin.webantpractice.common.user.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user.models.User
import bob.colbaskin.webantpractice.common.user.models.UserPreferences
import bob.colbaskin.webantpractice.datastore.AuthStatus
import bob.colbaskin.webantpractice.datastore.OnboardingStatus
import bob.colbaskin.webantpractice.datastore.UserPreferencesProto

fun UserPreferencesProto.toData(): UserPreferences {
    return UserPreferences(
        onboardingStatus = when (this.onboardingStatus) {
            OnboardingStatus.NOT_STARTED -> OnboardingConfig.NOT_STARTED
            OnboardingStatus.IN_PROGRESS -> OnboardingConfig.IN_PROGRESS
            OnboardingStatus.COMPLETED -> OnboardingConfig.COMPLETED
            OnboardingStatus.UNRECOGNIZED, null -> OnboardingConfig.NOT_STARTED
        },
        authStatus = when (this.authStatus) {
            AuthStatus.AUTHENTICATED -> AuthConfig.AUTHENTICATED
            AuthStatus.NOT_AUTHENTICATED -> AuthConfig.NOT_AUTHENTICATED
            AuthStatus.UNRECOGNIZED, null -> AuthConfig.NOT_AUTHENTICATED
        },
        userId = this.userId,
        username = this.username,
        birthDateMs = this.birthDateMs,
        phone = this.phone,
        email = this.email,
        avatarUrl = this.avatarUrl,
    )
}

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