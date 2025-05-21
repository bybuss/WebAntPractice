package bob.colbaskin.webantpractice.design_system

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme

@Composable
fun FilledButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CustomTheme.shapes.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                isLoading -> CustomTheme.colors.black
                isPressed -> CustomTheme.colors.main
                else -> CustomTheme.colors.black
            },
            contentColor = CustomTheme.colors.white,
            disabledContainerColor =
                if (isLoading) CustomTheme.colors.black else CustomTheme.colors.grayLight,
            disabledContentColor = CustomTheme.colors.gray
        ),
        enabled = enabled && !isLoading,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = stringResource(text),
                style = CustomTheme.typography.h4,
                modifier = Modifier.alpha(if (isLoading) 0f else 1f)
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = CustomTheme.colors.white,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 1.dp
                )
            }
        }
    }

}

@Composable
fun CustomOutlinedButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = CustomTheme.shapes.button,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = when {
                isLoading -> CustomTheme.colors.black
                !enabled -> CustomTheme.colors.grayLight
                isPressed -> CustomTheme.colors.main
                else -> CustomTheme.colors.black
            },
            disabledContentColor = CustomTheme.colors.gray
        ),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                isLoading -> CustomTheme.colors.black
                !enabled -> CustomTheme.colors.grayLight
                isPressed -> CustomTheme.colors.main
                else -> CustomTheme.colors.black
            }
        ),
        enabled = enabled && !isLoading,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = stringResource(text),
                style = CustomTheme.typography.h4,
                modifier = Modifier.alpha(if (isLoading) 0f else 1f)
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = CustomTheme.colors.black,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 1.dp
                )
            }
        }
    }
}

@Composable
fun CustomTextButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    TextButton(
        onClick = onClick,
        modifier = modifier,
        shape = CustomTheme.shapes.button,
        colors = ButtonDefaults.textButtonColors(
            contentColor = when {
                isLoading -> CustomTheme.colors.black
                !enabled -> CustomTheme.colors.grayLight
                isPressed -> CustomTheme.colors.main
                else -> CustomTheme.colors.black
            },
            disabledContentColor = CustomTheme.colors.gray
        ),
        enabled = enabled && !isLoading,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
        ) {
            Text(
                text = stringResource(text),
                style = CustomTheme.typography.h4,
                modifier = Modifier.alpha(if (isLoading) 0f else 1f)
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = CustomTheme.colors.black,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 1.dp
                )
            }
        }
    }
}

@Composable
fun FABButton() {
    // TODO: Implement
}

@Composable
fun TabButton() {
    // TODO: Implement
}

@Preview(showBackground = true)
@Composable
fun ButtonsPreview() {
    WebAntPracticeTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FilledButton(
                text = R.string.sign_up,
                onClick = { },
                enabled = true,
                isLoading = false
            )
            CustomOutlinedButton(
                text = R.string.sign_in,
                onClick = { },
                enabled = true,
                isLoading = false
            )
            CustomTextButton(
                text = R.string.sign_in,
                onClick = { },
                enabled = true,
                isLoading = false
            )
            FABButton()
            TabButton()
        }
    }
}
