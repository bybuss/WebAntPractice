package bob.colbaskin.webantpractice.design_system

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.design_system.utils.getColors

enum class DialogType {
    Default,
    WithIcon
}

@Composable
fun Dialog(
    type: DialogType = DialogType.Default,
    @StringRes dialogTitle: Int,
    @StringRes dialogText: Int,
    @DrawableRes icon: Int? = R.drawable.check_circle_filled,
    @StringRes actionLabel1: Int? = null,
    @StringRes actionLabel2: Int? = null,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        containerColor = CustomTheme.colors.white,
        titleContentColor = CustomTheme.colors.black,
        textContentColor = CustomTheme.colors.black,
        onDismissRequest = onDismissRequest,

        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (type == DialogType.WithIcon) {
                    icon?.let { iconRes ->
                        Icon(
                            painter = painterResource(iconRes),
                            contentDescription = stringResource(R.string.check_circle_filled_logo_description),
                            tint = CustomTheme.colors.green,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Text(
                    text = stringResource(dialogTitle),
                    style = MaterialTheme.typography.headlineSmall,
                )
                if (type == DialogType.WithIcon) {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            painter = painterResource(R.drawable.close),
                            contentDescription = stringResource(R.string.close_logo_description),
                            tint = CustomTheme.colors.gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        },
        text = {
            Text(
                text = stringResource(dialogText),
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        confirmButton = {
            actionLabel1?.let { label ->
                if (type == DialogType.Default) {
                    CustomTextButton(
                        text = label,
                        type = TextButtonType.Modal,
                        onClick = onConfirmation,
                    )
                } else {
                    FilledButton(
                        text = label,
                        onClick = onConfirmation,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        dismissButton = {
            if (type == DialogType.Default) {
                actionLabel2?.let { label ->
                    CustomTextButton(
                        text = label,
                        type = TextButtonType.Modal,
                        onClick = onDismissRequest,
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        colors = DatePickerDefaults.colors(containerColor = CustomTheme.colors.white),
        confirmButton = {
            CustomTextButton(
                text = R.string.dialog_ok,
                type = TextButtonType.Modal,
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
            )
        },
        dismissButton = {
            CustomTextButton(
                text = R.string.dialog_cancel,
                type = TextButtonType.Modal,
                onClick = onDismiss,
            )
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.getColors()
        )
    }
}

@Preview
@Composable
private fun DatePickerModalPreview() {
    WebAntPracticeTheme {
        DatePickerModal(
            onDateSelected = {},
            onDismiss = {}
        )
    }
}

@Preview
@Composable
private fun DialogPreview() {
    WebAntPracticeTheme {
        /*Dialog(
            dialogTitle = R.string.dialog_confirmation,
            dialogText = R.string.dialog_exit_data_lost,
            actionLabel1 = R.string.dialog_exit,
            actionLabel2 = R.string.dialog_cancel,
            onDismissRequest = {},
            onConfirmation = {}
        )*/
        Dialog(
            type = DialogType.WithIcon,
            dialogTitle = R.string.dialog_confirmation,
            dialogText = R.string.dialog_exit_data_lost,
            actionLabel1 = R.string.dialog_confirmation,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}