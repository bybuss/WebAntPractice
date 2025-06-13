package bob.colbaskin.webantpractice.home.domain

import bob.colbaskin.webantpractice.home.data.models.PhotosResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
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
    suspend fun getFile(
        @Path("path") path: String
    ): ResponseBody
}
