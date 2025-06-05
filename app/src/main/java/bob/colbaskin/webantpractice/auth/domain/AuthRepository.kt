package bob.colbaskin.webantpractice.auth.domain

import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User

interface AuthRepository {

    suspend fun login(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String
    )

    suspend fun refresh(
        refreshToken: String,
        clientId: String,
        clientSecret: String? = null
    )

    suspend fun register(
        email: String,
        birthday: String,
        displayName: String,
        phone: String,
        plainPassword: String
    ): Result<User>
}