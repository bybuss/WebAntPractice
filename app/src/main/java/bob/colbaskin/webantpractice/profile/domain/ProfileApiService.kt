package bob.colbaskin.webantpractice.profile.domain

import bob.colbaskin.webantpractice.profile.data.models.UserResponse
import retrofit2.http.GET

interface ProfileApiService {

    @GET("/users/{id}")
    suspend fun getUserById(id: Int): UserResponse
}