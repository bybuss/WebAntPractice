package bob.colbaskin.webantpractice.auth.data

import android.content.Context
import bob.colbaskin.webantpractice.auth.data.models.UserResponse
import bob.colbaskin.webantpractice.auth.data.models.toDomain
import bob.colbaskin.webantpractice.auth.domain.AuthApiService
import bob.colbaskin.webantpractice.auth.domain.AuthRepository
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.user_prefs.data.UserPreferencesRepositoryImpl
import bob.colbaskin.webantpractice.common.utils.safeApiCall
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val authApi: AuthApiService,
    private val dataStore: UserPreferencesRepositoryImpl
): AuthRepository {

    override suspend fun login(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String
    ) {
        val response = authApi.login(
            username = username,
            password = password,
            clientId = clientId,
        )
    }

    override suspend fun refresh(
        refreshToken: String,
        clientId: String,
        clientSecret: String?
    ) {
        val response = authApi.refresh(
            refreshToken = refreshToken,
            clientId = clientId
        )
    }

    override suspend fun register(
        email: String,
        birthday: String,
        displayName: String,
        phone: String,
        plainPassword: String
    ): Result<User> {
        return safeApiCall<UserResponse, User>(
            apiCall = {
                authApi.register(
                    email = email,
                    birthday = birthday,
                    displayName = displayName,
                    phone = phone,
                    plainPassword = plainPassword
                )
            },
            successHandler = { response ->
                val user = response.toDomain()
                dataStore.saveUser(user)
                user
            },
            context = context
        )
    }
}
