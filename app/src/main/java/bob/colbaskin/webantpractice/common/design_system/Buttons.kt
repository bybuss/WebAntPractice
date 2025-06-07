package bob.colbaskin.webantpractice.common.design_system

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.common.design_system.utils.getTextButtonColors

enum class TextButtonType {
    Default,
    Tab,
    Pink
}

@Composable
fun FilledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
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
                text = text,
                style = CustomTheme.typography.h4,
                modifier = Modifier.alpha(if (isLoading) 0f else 1f)
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = CustomTheme.colors.white,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }

}

@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(40.dp),
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
                text = text,
                style = CustomTheme.typography.h4,
                modifier = Modifier.alpha(if (isLoading) 0f else 1f)
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = CustomTheme.colors.black,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Composable
fun CustomTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = CustomTheme.typography.h4,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    type: TextButtonType = TextButtonType.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isPressed = interactionSource.collectIsPressedAsState().value

    TextButton(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = CustomTheme.shapes.button,
        colors = ButtonDefaults.getTextButtonColors(
            type = type,
            isLoading = isLoading,
            isSelected = isSelected,
            isPressed = isPressed,
            enabled = enabled
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
                text = text,
                style = when {
                    type == TextButtonType.Default -> CustomTheme.typography.h4
                    type == TextButtonType.Tab -> CustomTheme.typography.h4
                    type == TextButtonType.Pink -> MaterialTheme.typography.labelLarge
                    else -> textStyle
                },
                modifier = Modifier.alpha(if (isLoading) 0f else 1f)
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = CustomTheme.colors.black,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp
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
        onClick = { if (enabled) onClick() },
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
fun TabButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    val lineColor = when {
        isPressed -> CustomTheme.colors.main
        isSelected -> CustomTheme.colors.main
        !isSelected -> CustomTheme.colors.gray
        else -> CustomTheme.colors.black
    }

    CustomTextButton(
        type = TextButtonType.Tab,
        text = text,
        onClick = onClick,
        isSelected = isSelected,
        modifier = modifier
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth - 9
                drawLine(
                    color = lineColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            },
        interactionSource = interactionSource
    )
}

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes painterId: Int,
    contentDescriptionId: String?,
    enabled: Boolean = true,
    defaultIconColor: Color = CustomTheme.colors.graySecondary,
    clickedIconColor: Color = CustomTheme.colors.main,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = when {
                isPressed -> clickedIconColor
                else -> defaultIconColor
            },
            disabledContainerColor = Color.Transparent,
            disabledContentColor = CustomTheme.colors.gray
        ),
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painterResource(painterId),
            contentDescription = contentDescriptionId,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonsPreview() {
    WebAntPracticeTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FilledButton(
                text = stringResource(R.string.sign_up),
                onClick = { },
                enabled = true,
                isLoading = false
            )
            Spacer(modifier = Modifier.size(10.dp))
            FilledButton(
                text = stringResource(R.string.sign_up),
                onClick = { },
                enabled = true,
                isLoading = true
            )
            Spacer(modifier = Modifier.size(20.dp))
            CustomOutlinedButton(
                text = stringResource(R.string.sign_in),
                onClick = { },
                enabled = true,
                isLoading = false
            )
            Spacer(modifier = Modifier.size(20.dp))
            CustomTextButton(
                text = stringResource(R.string.sign_in),
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
            TabButton(
                text = stringResource(R.string.sign_up),
                onClick = { },
                isSelected = true
            )
            Spacer(modifier = Modifier.size(10.dp))
            TabButton(
                text = stringResource(R.string.sign_up),
                onClick = { },
                isSelected = false
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}
