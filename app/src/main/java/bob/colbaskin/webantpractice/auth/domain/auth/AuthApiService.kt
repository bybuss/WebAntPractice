package bob.colbaskin.webantpractice.auth.domain.auth

import bob.colbaskin.webantpractice.BuildConfig
import bob.colbaskin.webantpractice.auth.data.models.RegisterRequestBody
import bob.colbaskin.webantpractice.auth.data.models.TokenResponse
import bob.colbaskin.webantpractice.auth.data.models.UserResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("/token")
    suspend fun login(
        @Field("grant_type") grantType: String = "password",
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET
    ): TokenResponse

    @POST("/users")
    suspend fun register(@Body body: RegisterRequestBody): UserResponse
}
