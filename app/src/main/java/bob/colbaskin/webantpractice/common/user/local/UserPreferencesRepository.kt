package bob.colbaskin.webantpractice.common.user.local

import bob.colbaskin.webantpractice.common.user.models.AuthConfig
import bob.colbaskin.webantpractice.common.user.models.OnBoardingConfig
import bob.colbaskin.webantpractice.common.user.models.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getUserPreference(): Flow<UserPreferences>

    suspend fun saveUserAuthStatus(status: AuthConfig)

    suspend fun saveOnBoardingStatus(status: OnBoardingConfig)
}