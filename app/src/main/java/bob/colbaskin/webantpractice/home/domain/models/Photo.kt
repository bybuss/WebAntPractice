package bob.colbaskin.webantpractice.home.domain.models

data class Photo(
    val id: Int,
    val file: PhotoFile,
    val new: Boolean?,
    val popular: Boolean?,
)

data class PhotoFile(
    val id: Int,
    val path: String,
)
