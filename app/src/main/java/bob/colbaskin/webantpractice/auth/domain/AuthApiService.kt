package bob.colbaskin.webantpractice.auth.domain

import bob.colbaskin.webantpractice.auth.data.models.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("/token")
    fun login(
        @Field("grant_type") grantType: String = "password",
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String? = null
    ): String

    @FormUrlEncoded
    @POST("/token")
    fun refresh(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String? = null
    ): String

    @POST("/users")
    fun register(
        email: String,
        birthday: String,
        displayName: String,
        phone: String,
        plainPassword: String
    ): UserResponse
}