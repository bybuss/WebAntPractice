package bob.colbaskin.webantpractice.design_system.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

val LocalShapes = compositionLocalOf { Shapes }

object Shapes {
    val button = RoundedCornerShape(10.dp)
    val FAB = RoundedCornerShape(20.dp)
    val textField = RoundedCornerShape(10.dp)
        val search = RoundedCornerShape(28.dp)
        val card = RoundedCornerShape(28.dp)
        val photoSmall = RoundedCornerShape(2.dp)
        val photoBig = RoundedCornerShape(10.dp)
    }