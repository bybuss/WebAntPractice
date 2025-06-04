package bob.colbaskin.webantpractice.common.user.local

import bob.colbaskin.webantpractice.common.user.models.AuthConfig
import bob.colbaskin.webantpractice.common.user.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user.models.User
import bob.colbaskin.webantpractice.common.user.models.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getUserPreferences(): Flow<UserPreferences>

    fun getUser(): Flow<User>

    suspend fun saveAuthStatus(status: AuthConfig)

    suspend fun saveOnboardingStatus(status: OnboardingConfig)

    suspend fun saveUser(user: User)

    suspend fun saveUsername(username: String)

    suspend fun saveAvatarUrl(avatarUrl: String)

    suspend fun saveBirthDateMs(birthDateMs: Long)

    suspend fun savePhone(phone: String)

    suspend fun saveEmail(email: String)
}