package bob.colbaskin.webantpractice.home.data.models

import kotlinx.serialization.Serializable

@Serializable
data class FullPhotoResponse(
    val id: Int,
    val file: FullFileResponse,
    val user: UserResponse,
    val description: String,
    val name: String,
    val new: Boolean,
    val popular: Boolean,
    val dateCreate: String,
    val dateUpdate: String
)

@Serializable
data class FullFileResponse(
    val id: Int,
    val path: String,
    val dateCreate: String,
    val dateUpdate: String
)

@Serializable
data class UserResponse(
    val id: Int,
    val displayName: String,
    val dateCreate: String,
    val dateUpdate: String
)
