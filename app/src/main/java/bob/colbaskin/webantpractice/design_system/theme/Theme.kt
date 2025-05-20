package bob.colbaskin.webantpractice.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun WebAntPracticeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalTextStyles provides AppTextStyles,
        content = content
    )
}

object CustomTheme {
    val typography: AppTextStyles
        @Composable get() = LocalTextStyles.current
}