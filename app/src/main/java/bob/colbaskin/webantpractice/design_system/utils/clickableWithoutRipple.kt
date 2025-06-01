package bob.colbaskin.webantpractice.design_system.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    enabled: Boolean = true
): Modifier {
    return composed (
        factory = {
            this.then(
                Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { onClick() },
                    enabled = enabled
                )
            )
        }
    )
}
