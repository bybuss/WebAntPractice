package bob.colbaskin.webantpractice.design_system.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val black: Color,
    val gray: Color,
    val graySecondary: Color,
    val grayLight: Color,
    val graySearchDisabled: Color,
    val onSurface: Color,
    val white: Color,
    val main: Color,
    val gradient: List<Color>,
    val errorRed: Color,
    val blue: Color,
    val green: Color,
    val snackbarContainer: Color
)

val LocalColors = compositionLocalOf { lightColors }

val lightColors = AppColors(
    black = Color(0xFF1D1D1D),
    gray = Color(0xFFBCBCBC),
    graySecondary = Color(0xFF7a7a7e),
    grayLight = Color(0xFFEEEEEF),
    graySearchDisabled = Color(0xFFcecece),
    onSurface = Color(0xFF49454f),
    white = Color(0xFFFFFFFF),
    main = Color(0xFFCF497E),
    gradient = listOf(Color(0xFFE69633), Color(0xFFCF497E)),
    errorRed = Color(0xFFED3E3E),
    blue = Color(0xFF409EFF),
    green = Color(0xFF1ab46b),
    snackbarContainer = Color(0xFF717171)
)

val darkColors = AppColors(
    black = Color(0xFF1D1D1D),
    gray = Color(0xFFBCBCBC),
    graySecondary = Color(0xFF7a7a7e),
    grayLight = Color(0xFFEEEEEF),
    graySearchDisabled = Color(0xFFcecece),
    onSurface = Color(0xFF49454f),
    white = Color(0xFFFFFFFF),
    main = Color(0xFFCF497E),
    gradient = listOf(Color(0xFFE69633), Color(0xFFCF497E)),
    errorRed = Color(0xFFED3E3E),
    blue = Color(0xFF409EFF),
    green = Color(0xFF1ab46b),
    snackbarContainer = Color(0xFF717171)
)
