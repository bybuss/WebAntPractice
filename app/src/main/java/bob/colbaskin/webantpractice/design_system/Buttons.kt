package bob.colbaskin.webantpractice.design_system

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
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
fun FABButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    FloatingActionButton(
        onClick = { if (enabled) onClick else {} },
        modifier = modifier,
        shape = CustomTheme.shapes.FAB,
        containerColor = when {
            !enabled -> CustomTheme.colors.grayLight
            isPressed -> CustomTheme.colors.main
            else -> CustomTheme.colors.black
        },
        contentColor = when {
            !enabled -> CustomTheme.colors.gray
            else -> CustomTheme.colors.white
        },
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = when {
                isPressed -> 0.dp
                !enabled -> 0.dp
                else -> 4.dp
            },
            pressedElevation = when {
                !enabled -> 0.dp
                isPressed -> 0.dp
                else -> 8.dp
            }
        ),
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painterResource(R.drawable.add_new),
            contentDescription = stringResource(R.string.add_new_logo),
        )
    }
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
            Spacer(modifier = Modifier.size(20.dp))
            FABButton(
                onClick = { },
                enabled = true
            )
            Spacer(modifier = Modifier.size(20.dp))
            TabButton()
        }
    }
}