package bob.colbaskin.webantpractice.home.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosResponse(
    @SerialName("hydra:member") val hydraMember: List<PhotoResponse>
)

@Serializable
data class PhotoResponse(
    val id: Int,
    val file: PhotoFileResponse,
    val new: Boolean,
    val popular: Boolean
)

@Serializable
data class PhotoFileResponse(
    val id: Int,
    val path: String,
)
