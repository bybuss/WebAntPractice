package bob.colbaskin.webantpractice.home.domain

import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.home.domain.models.FullPhoto
import bob.colbaskin.webantpractice.home.domain.models.Photo

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
}
