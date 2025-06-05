package bob.colbaskin.webantpractice.auth.data

import android.content.Context
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.auth.data.models.UserResponse
import bob.colbaskin.webantpractice.auth.data.models.toDomain
import bob.colbaskin.webantpractice.auth.domain.AuthApiService
import bob.colbaskin.webantpractice.auth.domain.AuthRepository
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.user_prefs.data.UserPreferencesRepositoryImpl
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
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
        return try {
            val response: UserResponse = authApi.register(
                email = email,
                birthday = birthday,
                displayName = displayName,
                phone = phone,
                plainPassword = plainPassword
            )
            val user = response.toDomain()
            dataStore.saveUser(user)
            Result.Success(data = user)
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.Error(
                    title = context.getString(R.string.network_error_title),
                    text = e.message.toString()
                )
                is TimeoutCancellationException -> Result.Error(
                    title = context.getString(R.string.timeout_error_title),
                    text = e.message.toString()
                )
                is HttpException -> {
                    when (e.code()) {
                        400 -> Result.Error(
                            title = context.getString(R.string.error_title),
                            text = e.message().toString()
                        )
                        401 -> Result.Error(
                            title = context.getString(R.string.authorization_error_title),
                            text = context.getString(R.string.authorization_error_text)
                        )
                        in 500..599 -> Result.Error(
                            title = context.getString(R.string.server_error_title),
                            text = e.message().toString()
                        )
                        else -> Result.Error(
                            title = context.getString(R.string.error_title),
                            text = e.message().toString()
                        )
                    }
                }
                else -> Result.Error(
                    title = context.getString(R.string.generic_error_title),
                    text = e.message.toString()
                )
            }
        }
    }
}
