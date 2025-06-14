package bob.colbaskin.webantpractice.home.domain.models

import androidx.compose.ui.graphics.ImageBitmap
import bob.colbaskin.webantpractice.common.UiState

data class FullPhoto(
    val id: Int,
    val file: FullFile,
    val user: User,
    val description: String,
    val name: String,
    val new: Boolean,
    val popular: Boolean,
    val dateCreate: Long,
    val dateUpdate: Long,
    val imageState: UiState<ImageBitmap> = UiState.Loading
)

data class FullFile(
    val id: Int,
    val path: String,
    val dateCreate: Long,
    val dateUpdate: Long
)

data class User(
    val id: Int,
    val displayName: String,
    val dateCreate: Long,
    val dateUpdate: Long
)
