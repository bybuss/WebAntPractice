package bob.colbaskin.webantpractice.auth.domain.token

import bob.colbaskin.webantpractice.BuildConfig
import bob.colbaskin.webantpractice.auth.data.models.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RefreshTokenService {

    @FormUrlEncoded
    @POST("/token")
    fun refresh(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET
    ): TokenResponse
}