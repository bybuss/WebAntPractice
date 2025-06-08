package bob.colbaskin.webantpractice.common.design_system

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.common.design_system.utils.clickableWithoutRipple

@Composable
fun CustomCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.clickableWithoutRipple(
            interactionSource = interactionSource,
            onClick = { onCheckedChange(!checked) }
        )
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = CustomTheme.colors.main,
                uncheckedColor = CustomTheme.colors.gray,
                checkmarkColor = CustomTheme.colors.white,
                disabledCheckedColor = CustomTheme.colors.main,
                disabledUncheckedColor = CustomTheme.colors.gray,
            ),
            interactionSource = interactionSource
        )
        Text(
            text = text,
            style = if (checked) CustomTheme.typography.h4 else CustomTheme.typography.p,
            color = when {
                isPressed -> CustomTheme.colors.main
                else -> CustomTheme.colors.black
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomCheckboxPreview() {
    WebAntPracticeTheme {
        var checked1 by remember { mutableStateOf(true) }
        var checked2 by remember { mutableStateOf(false) }

        Column {
            CustomCheckbox(
                text = stringResource(R.string.checkbox_new),
                checked = checked1,
                onCheckedChange = { checked1 = it }
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomCheckbox(
                text = stringResource(R.string.checkbox_popular),
                checked = checked2,
                onCheckedChange = { checked2 = it }
            )
        }
    }
}