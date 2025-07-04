package bob.colbaskin.webantpractice.auth.data

import android.content.Context
import android.util.Log
import bob.colbaskin.webantpractice.auth.data.models.ChangePasswordBody
import bob.colbaskin.webantpractice.auth.data.models.RegisterRequestBody
import bob.colbaskin.webantpractice.auth.data.models.TokenResponse
import bob.colbaskin.webantpractice.auth.data.models.UpdateUserBody
import bob.colbaskin.webantpractice.auth.data.models.UserResponse
import bob.colbaskin.webantpractice.auth.data.models.toDomain
import bob.colbaskin.webantpractice.auth.domain.auth.AuthApiService
import bob.colbaskin.webantpractice.auth.domain.auth.AuthRepository
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.webantpractice.common.user_prefs.domain.UserPreferencesRepository
import bob.colbaskin.webantpractice.common.utils.formatFromMillis
import bob.colbaskin.webantpractice.common.utils.safeApiCall
import bob.colbaskin.webantpractice.di.token.TokenManager
import javax.inject.Inject

private const val TAG = "Auth"

class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val authApi: AuthApiService,
    private val userPreferences: UserPreferencesRepository,
    private val tokenManager: TokenManager
): AuthRepository {

    override suspend fun login(username: String, password: String): Result<Unit> {
        Log.d(TAG, "Attempting login for user: $username")
        return safeApiCall<TokenResponse, Unit>(
            apiCall = {
                authApi.login(
                    username = username,
                    password = password
                )
            },
            successHandler = {  response ->
                Log.d(TAG, "Login successful. Saving tokens & Authenticated status")
                userPreferences.saveAuthStatus(AuthConfig.AUTHENTICATED)
                tokenManager.saveTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            },
            context = context
        )
    }

    override suspend fun getCurrentUser(): Result<User> {
        Log.d(TAG, "Saving current user")
        return safeApiCall<UserResponse, User>(
            apiCall = { authApi.getCurrentUser() },
            successHandler = { response ->
                val user = response.toDomain()
                Log.d(TAG, "Successfully fetched user: $user")
                user
            },
            context = context
        )
    }

    override suspend fun register(
        email: String,
        birthday: Long,
        displayName: String,
        phone: String,
        plainPassword: String
    ): Result<User> {
        Log.d(TAG, "Registering new user: $email")
        return safeApiCall<UserResponse, User>(
            apiCall = {
                authApi.register(
                    RegisterRequestBody(
                        email = email,
                        birthday = birthday.formatFromMillis(),
                        displayName = displayName,
                        phone = phone,
                        plainPassword = plainPassword
                    )
                )
            },
            successHandler = { response ->
                userPreferences.saveAuthStatus(AuthConfig.AUTHENTICATED)
                val user = response.toDomain()
                Log.d(TAG, "Registration successful. Saving user data & Authenticated status\n$user")
                user
            },
            context = context
        )
    }

    override suspend fun updateUser(
        id: Int,
        email: String,
        birthday: Long,
        displayName: String,
        phone: String,
    ): Result<User> {
        Log.d(TAG, "Updating user: $id")
        return safeApiCall<UserResponse, User>(
            apiCall = {
                authApi.updateUser(
                    id = id,
                    body = UpdateUserBody(
                        email = email,
                        birthday = birthday.formatFromMillis(),
                        displayName = displayName,
                        phone = phone
                    )
                )
            },
            successHandler = { response ->
                val user = response.toDomain()
                Log.d(TAG, "User updated: $user")
                user
            },
            context = context
        )
    }

    override suspend fun changePassword(
        id: Int,
        oldPassword: String,
        newPassword: String
    ): Result<User> {
        Log.d(TAG, "Changing password for user: $id")
        return safeApiCall<UserResponse, User>(
            apiCall = {
                authApi.changePassword(
                    id = id,
                    body = ChangePasswordBody(
                        oldPassword = oldPassword,
                        plainPassword = newPassword
                    )
                )
            },
            successHandler = { response ->
                val user = response.toDomain()
                Log.d(TAG, "Password changed for user: $id")
                user
            },
            context = context
        )
    }
}
