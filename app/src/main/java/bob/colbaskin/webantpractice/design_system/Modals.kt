package bob.colbaskin.webantpractice.design_system

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.design_system.utils.getColors

@Composable
fun Dialog() {
    // TODO implement
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.ok),
                    style = MaterialTheme.typography.labelLarge,
                    color = CustomTheme.colors.main
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.labelLarge,
                    color = CustomTheme.colors.main
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = CustomTheme.colors.white,
        )
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