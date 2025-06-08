package bob.colbaskin.webantpractice.common.user_prefs.domain

import bob.colbaskin.webantpractice.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getUserPreferences(): Flow<UserPreferences>

    fun getUser(): Flow<User>

    suspend fun saveAuthStatus(status: AuthConfig)

    suspend fun saveOnboardingStatus(status: OnboardingConfig)

    suspend fun saveUser(user: User)

    suspend fun saveUsername(username: String)

    suspend fun saveAvatarUrl(avatarUrl: String?)

    suspend fun saveBirthDateMs(birthDateMs: Long)

    suspend fun savePhone(phone: String)

    suspend fun saveEmail(email: String)
}