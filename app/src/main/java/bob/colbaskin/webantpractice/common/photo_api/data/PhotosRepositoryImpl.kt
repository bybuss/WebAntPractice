package bob.colbaskin.webantpractice.common.photo_api.data

import android.content.Context
import android.util.Log
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.utils.safeApiCall
import bob.colbaskin.webantpractice.home.data.models.FullPhotoResponse
import bob.colbaskin.webantpractice.home.data.models.PhotoNameOnlyResponse
import bob.colbaskin.webantpractice.home.data.models.PhotosResponse
import bob.colbaskin.webantpractice.home.data.models.PhotoBody
import bob.colbaskin.webantpractice.home.data.toDomain
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosApiService
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosRepository
import bob.colbaskin.webantpractice.home.data.models.PhotoFileResponse
import bob.colbaskin.webantpractice.home.domain.models.FullPhoto
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.home.domain.models.PhotoFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    override suspend fun getPhotoNameById(id: Int): Result<String> {
        Log.d(TAG, "Fetching photo name for id: $id")
        return safeApiCall<PhotoNameOnlyResponse, String>(
            apiCall = {
                photosApi.getPhotoNameById(id)
            },
            successHandler = { it.name },
            context = context
        )
    }

    override suspend fun getPhotoById(id: Int): Result<FullPhoto> {
        Log.d(TAG, "Fetching full photo for id: $id")
        return safeApiCall<FullPhotoResponse, FullPhoto>(
            apiCall = { photosApi.getPhotoById(id) },
            successHandler = { it.toDomain() },
            context = context
        )
    }

    override suspend fun updatePhoto(
        id: Int,
        fileId: Int,
        userId: Int,
        description: String,
        name: String,
        isNew: Boolean,
        isPopular: Boolean
    ): Result<FullPhoto> {
        Log.d(TAG, "Updating photo for id: $id")
        return safeApiCall<FullPhotoResponse, FullPhoto>(
            apiCall = {
                photosApi.updatePhoto(
                    id = id,
                    body = PhotoBody(
                        file = "/files/$fileId",
                        user = "/users/$userId",
                        description = description,
                        name = name,
                        new = isNew,
                        popular = isPopular
                    )
                )
            },
            successHandler = { it.toDomain() },
            context = context
        )
    }

    override suspend fun uploadFile(
        originalName: String,
        file: ByteArray
    ): Result<PhotoFile> {
        Log.d(TAG, "Uploading file: $originalName")
        val originalNamePart = MultipartBody.Part.createFormData(
            name = "originalName",
            filename = null,
            body = originalName.toRequestBody("text/plain".toMediaTypeOrNull())
        )
        val filePart = MultipartBody.Part.createFormData(
            name = "file",
            filename = originalName,
            body = file.toRequestBody("image/*".toMediaTypeOrNull())
        )
        return safeApiCall<PhotoFileResponse, PhotoFile>(
            apiCall = {
                photosApi.uploadFile(originalNamePart, filePart)
            },
            successHandler = { it.toDomain() },
            context = context
        )
    }

    override suspend fun createPhoto(
        fileId: Int,
        userId: Int,
        description: String,
        name: String,
        isNew: Boolean,
        isPopular: Boolean
    ): Result<FullPhoto> {
        Log.d(TAG, "Creating new photo: $name")
        return safeApiCall<FullPhotoResponse, FullPhoto>(
            apiCall = {
                photosApi.createPhoto(
                    body = PhotoBody(
                        file = "/files/$fileId",
                        user = "/users/$userId",
                        description = description,
                        name = name,
                        new = isNew,
                        popular = isPopular
                    )
                )
            },
            successHandler = { it.toDomain() },
            context = context
        )
    }
}