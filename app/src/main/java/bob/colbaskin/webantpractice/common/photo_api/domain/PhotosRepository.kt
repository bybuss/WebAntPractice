package bob.colbaskin.webantpractice.common.photo_api.domain

import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.home.domain.models.FullPhoto
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.home.domain.models.PhotoFile

interface PhotosRepository {

    suspend fun getPhotos(
        page: Int,
        itemsPerPage: Int,
        order: String?,
        new: Boolean?,
        popular: Boolean?
    ): Result<List<Photo>>

    suspend fun getFile(path: String): Result<ByteArray>

    suspend fun getPhotoNameById(id: Int): Result<String>

    suspend fun getPhotoById(id: Int): Result<FullPhoto>

    suspend fun updatePhoto(
        id: Int,
        fileId: Int,
        userId: Int,
        description: String,
        name: String,
        isNew: Boolean,
        isPopular: Boolean
    ): Result<FullPhoto>

    suspend fun uploadFile(originalName: String, file: ByteArray): Result<PhotoFile>

    suspend fun createPhoto(
        fileId: Int,
        userId: Int,
        description: String,
        name: String,
        isNew: Boolean,
        isPopular: Boolean
    ): Result<FullPhoto>
}
