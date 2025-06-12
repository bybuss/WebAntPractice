package bob.colbaskin.webantpractice.auth.domain.token

import bob.colbaskin.webantpractice.common.Result

interface RefreshTokenRepository {

    suspend fun refresh(refreshToken: String): Result<Unit>
}