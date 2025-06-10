package bob.colbaskin.webantpractice.common.user_prefs.data

import bob.colbaskin.webantpractice.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import bob.colbaskin.webantpractice.datastore.AuthStatus
import bob.colbaskin.webantpractice.datastore.OnboardingStatus
import bob.colbaskin.webantpractice.datastore.UserPreferencesProto

fun UserPreferencesProto.toData(): UserPreferences {
    return UserPreferences(
        onboardingStatus = when (this.onboardingStatus) {
            OnboardingStatus.NOT_STARTED -> OnboardingConfig.NOT_STARTED
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