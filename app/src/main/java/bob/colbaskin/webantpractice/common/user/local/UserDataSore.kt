package bob.colbaskin.webantpractice.common.user.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import bob.colbaskin.webantpractice.common.user.models.AuthConfig
import bob.colbaskin.webantpractice.common.user.models.OnBoardingConfig
import bob.colbaskin.webantpractice.datastore.AuthStatus
import bob.colbaskin.webantpractice.datastore.OnboardingStatus
import bob.colbaskin.webantpractice.datastore.UserPreferencesProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

private const val USER_PREFERENCES_FILE_NAME = "user_prefs.pb"
private val TAG = "UserPreferences"

val Context.userPreferencesStore: DataStore<UserPreferencesProto> by dataStore(
    fileName = USER_PREFERENCES_FILE_NAME,
    serializer = UserPreferencesSerializer
)

class UserDataStore(
    private val context: Context
) {
    private val dataStore: DataStore<UserPreferencesProto> = context.userPreferencesStore
    val userPreferencesFlow: Flow<UserPreferencesProto> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(UserPreferencesProto.getDefaultInstance())
            } else {
                throw exception
            }
        }

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
    suspend fun saveOnBoardingStatus(status: OnBoardingConfig) {
        dataStore.updateData { prefs ->
            prefs.toBuilder().apply {
                onboardingStatus = when (status) {
                    OnBoardingConfig.NOT_STARTED -> OnboardingStatus.NOT_STARTED
                    OnBoardingConfig.IN_PROGRESS -> OnboardingStatus.IN_PROGRESS
                    OnBoardingConfig.COMPLETED -> OnboardingStatus.COMPLETED
                }
            }.build()
        }
    }

    suspend fun saveUserData(
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
