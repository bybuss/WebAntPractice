package bob.colbaskin.webantpractice.home.domain

import bob.colbaskin.webantpractice.common.Result
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
}
