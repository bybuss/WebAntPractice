package bob.colbaskin.webantpractice.auth.domain.auth

import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User

interface AuthRepository {

    suspend fun login(username: String, password: String): Result<Unit>

    suspend fun register(
        email: String,
        birthday: Long,
        displayName: String,
        phone: String,
        plainPassword: String
    ): Result<User>
}
