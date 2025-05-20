package bob.colbaskin.webantpractice.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun calculateImageScaleToFullscreen(
    image: ImageBitmap,
    screenWidth: Float,
    screenHeight: Float
): Float {

    val imageWidth = image.width
    val imageHeight = image.height

    val scaleX = screenWidth / imageWidth
    val scaleY = screenHeight / imageHeight

    return maxOf(scaleX, scaleY)
}

@Composable
fun calculateImageScaleToFullscreen(
    image: Painter,
    screenWidth: Float,
    screenHeight: Float
): Float {

    val imageWidth = image.intrinsicSize.width
    val imageHeight = image.intrinsicSize.height

    val scaleX = screenWidth / imageWidth
    val scaleY = screenHeight / imageHeight

    return maxOf(scaleX, scaleY)
}
