package bob.colbaskin.webantpractice.home.data

import android.content.Context
import android.util.Log
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.utils.safeApiCall
import bob.colbaskin.webantpractice.home.data.models.PhotosResponse
import bob.colbaskin.webantpractice.home.domain.PhotosApiService
import bob.colbaskin.webantpractice.home.domain.PhotosRepository
import bob.colbaskin.webantpractice.home.domain.models.Photo
import javax.inject.Inject

private const val TAG = "Photos"

class PhotosRepositoryImpl @Inject constructor(
    private val context: Context,
    private val photosApi: PhotosApiService,
) : PhotosRepository {

    override suspend fun getPhotos(
        page: Int,
        itemsPerPage: Int,
        order: String?,
        new: Boolean?,
        popular: Boolean?
    ): Result<List<Photo>> {
        Log.d(TAG, "Getting photos from API...")
        return safeApiCall<PhotosResponse, List<Photo>>(
            apiCall = {
                photosApi.getPhotos(
                    page = page,
                    itemsPerPage = itemsPerPage,
                    order = order,
                    new = new,
                    popular = popular
                )
            },
            successHandler = { response ->
                val photos = response.toDomain()
                Log.d(TAG, "Photos fetched: ${photos.size} items")
                photos
            },
            context = context
        )
    }

    override suspend fun getFile(path: String): Result<ByteArray> {
        Log.d(TAG, "Fetching image data for path: $path")
        return safeApiCall(
            apiCall = { photosApi.getFile(path) },
            successHandler = { it.byteStream().readBytes() },
            context = context
        )
    }
}
