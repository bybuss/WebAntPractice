package bob.colbaskin.webantpractice.common.user_prefs.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import bob.colbaskin.webantpractice.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.webantpractice.common.user_prefs.data.toData
import bob.colbaskin.webantpractice.datastore.AuthStatus
import bob.colbaskin.webantpractice.datastore.OnboardingStatus
import bob.colbaskin.webantpractice.datastore.UserPreferencesProto
import bob.colbaskin.webantpractice.datastore.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_FILE_NAME = "user_preferences.pb"
private const val TAG = "UserPreferences"

val Context.userPreferencesStore: DataStore<UserPreferencesProto> by dataStore(
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

    suspend fun saveUser(
        userId: Int,
        username: String,
        birthDateMs: Long,
        phone: String,
        email: String,
        avatarUrl: String? = null
    ) {
        Log.d(TAG, "saveUser: $userId, $username, $birthDateMs, $phone, $email, $avatarUrl")
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.userId = userId
                this.username = username
                this.birthDateMs = birthDateMs
                this.phone = phone
                this.email = email
                this.avatarUrl = avatarUrl
            }.build()
        }
    }

    suspend fun saveUsername(username: String) {
        Log.d(TAG, "saveUsername: $username")
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.username = username
            }.build()
        }
    }
    suspend fun saveAvatarUrl(avatarUrl: String?) {
        Log.d(TAG, "saveAvatarUrl: $avatarUrl")
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.avatarUrl = avatarUrl
            }.build()
        }
    }
    suspend fun saveBirthDateMs(birthDateMs: Long) {
        Log.d(TAG, "saveBirthDateMs: $birthDateMs")
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.birthDateMs = birthDateMs
            }.build()
        }
    }
    suspend fun savePhone(phone: String) {
        Log.d(TAG, "savePhone: $phone")
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.phone = phone
            }.build()
        }
    }
    suspend fun saveEmail(email: String) {
        Log.d(TAG, "saveEmail: $email")
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.email = email
            }.build()
        }
    }
}
