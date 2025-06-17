package bob.colbaskin.webantpractice.common.photo_api.domain

import bob.colbaskin.webantpractice.home.data.models.FullPhotoResponse
import bob.colbaskin.webantpractice.home.data.models.PhotoFileResponse
import bob.colbaskin.webantpractice.home.data.models.PhotoNameOnlyResponse
import bob.colbaskin.webantpractice.home.data.models.PhotosResponse
import bob.colbaskin.webantpractice.home.data.models.PhotoBody
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface PhotosApiService {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("itemsPerPage") itemsPerPage: Int,
        @Query("order[id]") order: String?,
        @Query("new") new: Boolean?,
        @Query("popular") popular: Boolean?
    ): PhotosResponse

    @GET("/get_file/{path}")
    @Streaming
    suspend fun getFile(@Path("path") path: String): ResponseBody

    @GET("/photos/{id}")
    suspend fun getPhotoNameById(@Path("id") id: Int): PhotoNameOnlyResponse

    @GET("/photos/{id}")
    suspend fun getPhotoById(@Path("id") id: Int): FullPhotoResponse

    @PATCH("/photos/{id}")
    suspend fun updatePhoto(
        @Path("id") id: Int,
        @Body body: PhotoBody
    ): FullPhotoResponse

    @Multipart
    @POST("/files")
    suspend fun uploadFile(
        @Part originalName: MultipartBody.Part,
        @Part file: MultipartBody.Part
    ): PhotoFileResponse

    @POST("/photos")
    suspend fun createPhoto(@Body body: PhotoBody): FullPhotoResponse
}
