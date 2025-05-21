package bob.colbaskin.webantpractice.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebAntPracticeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) darkColors else lightColors

    val rippleConfiguration = RippleConfiguration(
        rippleAlpha = RippleAlpha(
            pressedAlpha = 0f,
            draggedAlpha = 0f,
            focusedAlpha = 0f,
            hoveredAlpha = 0f
        )
    )

    CompositionLocalProvider(
        LocalTextStyles provides AppTextStyles,
        LocalShapes provides Shapes,
        LocalColors provides colors,
        LocalRippleConfiguration provides rippleConfiguration,
        content = content
    )
}

object CustomTheme {
    val typography: AppTextStyles
        @Composable get() = LocalTextStyles.current
    val shapes: Shapes
        @Composable get() = LocalShapes.current
    val colors: AppColors
        @Composable get() = LocalColors.current
}
