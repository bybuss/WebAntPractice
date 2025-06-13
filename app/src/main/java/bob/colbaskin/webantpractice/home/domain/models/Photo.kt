package bob.colbaskin.webantpractice.home.domain.models

import androidx.compose.ui.graphics.ImageBitmap
import bob.colbaskin.webantpractice.common.UiState

data class Photo(
    val id: Int,
    val file: PhotoFile,
    val new: Boolean,
    val popular: Boolean,
    val imageState: UiState<ImageBitmap> = UiState.Loading
)

data class PhotoFile(
    val id: Int,
    val path: String,
)
