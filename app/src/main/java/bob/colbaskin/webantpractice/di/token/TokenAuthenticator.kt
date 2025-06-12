package bob.colbaskin.webantpractice.di.token

import android.util.Log
import bob.colbaskin.webantpractice.auth.domain.token.RefreshTokenRepository
import bob.colbaskin.webantpractice.common.Result
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import dagger.Lazy
import javax.inject.Inject

private const val TAG = "Auth"

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenRepository: Lazy<RefreshTokenRepository>
): Authenticator  {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d(TAG, "Authentication required (HTTP ${response.code})")
        val refreshToken = tokenManager.getRefreshToken() ?: return null

        return runBlocking {
            try {
                Log.d(TAG, "Attempting token refresh")
                val result = refreshTokenRepository.get().refresh(refreshToken = refreshToken)

                if (result is Result.Success) {
                    Log.d(TAG, "Token refresh successful")
                    tokenManager.getAccessToken()?.let { accessToken ->
                        return@runBlocking response.request.newBuilder()
                            .header("Authorization", "Bearer $accessToken")
                            .build()
                    }
                } else {
                    Log.d(TAG, "Token refresh failed")
                }
                null
            } catch (e: Exception) {
                Log.e(TAG, "Token refresh error: ${e.message}")
                null
            }
        }
    }
}