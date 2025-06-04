package bob.colbaskin.webantpractice.common.user.local

import bob.colbaskin.webantpractice.common.user.models.AuthConfig
import bob.colbaskin.webantpractice.common.user.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user.models.User
import bob.colbaskin.webantpractice.common.user.models.UserPreferences
import bob.colbaskin.webantpractice.common.user.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: UserDataStore
): UserPreferencesRepository {

    override fun getUserPreferences(): Flow<UserPreferences> = dataStore.getUserPreferences()

    override fun getUser(): Flow<User> = dataStore.getUserPreferences().map { it.toDomain() }

    override suspend fun saveAuthStatus(status: AuthConfig) = dataStore.saveAuthStatus(status)

    override suspend fun saveOnboardingStatus(status: OnboardingConfig)
        = dataStore.saveOnboardingStatus(status)

    override suspend fun saveUser(user: User) {
        return dataStore.saveUser(
            userId = user.id,
            username = user.displayName,
            birthDateMs = user.birthday,
            phone = user.phone,
            email = user.email,
            avatarUrl = user.userProfilePhoto,
        )
    }

    override suspend fun saveUsername(username: String) = dataStore.saveUsername(username)

    override suspend fun saveAvatarUrl(avatarUrl: String) = dataStore.saveAvatarUrl(avatarUrl)

    override suspend fun saveBirthDateMs(birthDateMs: Long) = dataStore.saveBirthDateMs(birthDateMs)

    override suspend fun savePhone(phone: String) = dataStore.savePhone(phone)

    override suspend fun saveEmail(email: String) = dataStore.saveEmail(email)
}