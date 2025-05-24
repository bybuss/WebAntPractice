package bob.colbaskin.webantpractice.design_system

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.design_system.utils.getColors

@Composable
fun Dialog(
    @StringRes dialogTitle: Int,
    @StringRes dialogText: Int,
    @StringRes actionLabel1: Int,
    @StringRes actionLabel2: Int,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        containerColor = CustomTheme.colors.white,
        titleContentColor = CustomTheme.colors.black,
        textContentColor = CustomTheme.colors.black,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(dialogTitle),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Text(
                text = stringResource(dialogText),
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        confirmButton = {
            CustomTextButton(
                text = actionLabel1,
                type = TextButtonType.Modal,
                onClick = onConfirmation,
            )
        },
        dismissButton = {
            CustomTextButton(
                text = actionLabel2,
                type = TextButtonType.Modal,
                onClick = onDismissRequest,
            )
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
        Dialog(
            dialogTitle = R.string.dialog_confirmation,
            dialogText = R.string.dialog_exit_data_lost,
            actionLabel1 = R.string.dialog_exit,
            actionLabel2 = R.string.dialog_cancel,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}