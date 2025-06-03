package bob.colbaskin.webantpractice.common.user.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import bob.colbaskin.webantpractice.common.user.models.AuthConfig
import bob.colbaskin.webantpractice.common.user.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user.models.UserPreferences
import bob.colbaskin.webantpractice.common.user.toData
import bob.colbaskin.webantpractice.datastore.AuthStatus
import bob.colbaskin.webantpractice.datastore.OnboardingStatus
import bob.colbaskin.webantpractice.datastore.UserPreferencesProto
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
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                authStatus = when (status) {
                    AuthConfig.NOT_AUTHENTICATED -> AuthStatus.NOT_AUTHENTICATED
                    AuthConfig.AUTHENTICATED -> AuthStatus.AUTHENTICATED
                }
            }.build()
        }
    }
    suspend fun saveOnboardingStatus(status: OnboardingConfig) {
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                onboardingStatus = when (status) {
                    OnboardingConfig.NOT_STARTED -> OnboardingStatus.NOT_STARTED
                    OnboardingConfig.IN_PROGRESS -> OnboardingStatus.IN_PROGRESS
                    OnboardingConfig.COMPLETED -> OnboardingStatus.COMPLETED
                }
            }.build()
        }
    }

    suspend fun saveUser(
        userId: Int,
        username: String,
        birthDateMs: Long,
        phone: String,
        email: String,
        avatarUrl: String
    ) {
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
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.username = username
            }.build()
        }
    }
    suspend fun saveAvatarUrl(avatarUrl: String) {
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.avatarUrl = avatarUrl
            }.build()
        }
    }
    suspend fun saveBirthDateMs(birthDateMs: Long) {
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.birthDateMs = birthDateMs
            }.build()
        }
    }
    suspend fun savePhone(phone: String) {
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.phone = phone
            }.build()
        }
    }
    suspend fun saveEmail(email: String) {
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                this.email = email
            }.build()
        }
    }
}
