package bob.colbaskin.webantpractice.home.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePhotoBody(
    val file: String,
    val user: String,
    val description: String,
    val name: String,
    val new: Boolean,
    val popular: Boolean
)