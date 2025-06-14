package bob.colbaskin.webantpractice.home.domain

import androidx.compose.ui.graphics.ImageBitmap
import androidx.paging.PagingData
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.home.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    fun getPhotosStream(
        new: Boolean? = null,
        popular: Boolean? = null
    ): Result<Flow<PagingData<Photo>>>

    suspend fun loadImage(photo: Photo): Result<ImageBitmap>
}
