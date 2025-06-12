package bob.colbaskin.webantpractice.auth.data

import android.content.Context
import android.util.Log
import bob.colbaskin.webantpractice.auth.data.models.TokenResponse
import bob.colbaskin.webantpractice.auth.domain.token.RefreshTokenRepository
import bob.colbaskin.webantpractice.auth.domain.token.RefreshTokenService
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.utils.safeApiCall
import bob.colbaskin.webantpractice.di.token.TokenManager
import javax.inject.Inject

private const val TAG = "Auth"

class RefreshTokenRepositoryImpl @Inject constructor(
    private val context: Context,
    private val refreshTokenApi: RefreshTokenService,
    private val tokenManager: TokenManager
): RefreshTokenRepository {

    override suspend fun refresh(refreshToken: String): Result<Unit> {
        Log.d(TAG, "Refreshing access token")
        return safeApiCall<TokenResponse, Unit>(
            apiCall = {
                refreshTokenApi.refresh(
                    refreshToken = refreshToken
                )
            },
            successHandler = {  response ->
                Log.d(TAG, "Token refresh successful. Saving new tokens")
                tokenManager.saveTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            },
            context = context
        )
    }
}