package bob.colbaskin.webantpractice.common.user_prefs.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import bob.colbaskin.webantpractice.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.webantpractice.common.user_prefs.data.models.toData
import bob.colbaskin.webantpractice.datastore.AuthStatus
import bob.colbaskin.webantpractice.datastore.OnboardingStatus
import bob.colbaskin.webantpractice.datastore.UserPreferencesProto
import bob.colbaskin.webantpractice.datastore.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_FILE_NAME = "user_preferences.pb"
private const val TAG = "UserPreferences"

private val Context.userPreferencesStore: DataStore<UserPreferencesProto> by dataStore(
    fileName = USER_PREFERENCES_FILE_NAME,
    serializer = UserPreferencesSerializer
)

class UserDataStore(context: Context) {
    private val dataStore: DataStore<UserPreferencesProto> = context.userPreferencesStore

    fun getUserPreferences(): Flow<UserPreferences> = dataStore.data.map { it.toData() }

    suspend fun saveAuthStatus(status: AuthConfig) {
        Log.d(TAG, "saveAuthStatus: $status")
        dataStore.updateData { prefs ->
            prefs.copy {
                authStatus = when (status) {
                    AuthConfig.NOT_AUTHENTICATED -> AuthStatus.NOT_AUTHENTICATED
                    AuthConfig.AUTHENTICATED -> AuthStatus.AUTHENTICATED
                }
            }
        }
    }
    suspend fun saveOnboardingStatus(status: OnboardingConfig) {
        Log.d(TAG, "saveOnboardingStatus: $status")
        dataStore.updateData { prefs ->
            prefs.copy {
                onboardingStatus  = when (status) {
                    OnboardingConfig.NOT_STARTED -> OnboardingStatus.NOT_STARTED
                    OnboardingConfig.COMPLETED -> OnboardingStatus.COMPLETED
                }
            }
        }
    }
}
