package bob.colbaskin.webantpractice.common.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.UiState

fun Result<ByteArray>.toImageBitmap(context: android.content.Context): UiState<ImageBitmap> {
    return when (this) {
        is Result.Success -> {
            val bitmap = BitmapFactory.decodeByteArray(this.data, 0, this.data.size)
            bitmap?.asImageBitmap()?.let { UiState.Success(it) }
                ?: UiState.Error(
                    context.getString(R.string.error_title),
                    context.getString(R.string.error_text)
                )
        }
        is Result.Error -> UiState.Error(this.title, this.text)
    }
}
