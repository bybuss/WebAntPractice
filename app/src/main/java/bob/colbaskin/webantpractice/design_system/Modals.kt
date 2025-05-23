package bob.colbaskin.webantpractice.design_system

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme

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
            colors = DatePickerDefaults.colors(
                containerColor = CustomTheme.colors.white,
                titleContentColor = CustomTheme.colors.black,
                headlineContentColor = CustomTheme.colors.black,
                weekdayContentColor = CustomTheme.colors.black,
                subheadContentColor = CustomTheme.colors.black,
                navigationContentColor = CustomTheme.colors.black,
                yearContentColor = CustomTheme.colors.black,
                disabledYearContentColor = CustomTheme.colors.gray,
                currentYearContentColor = CustomTheme.colors.main,
                selectedYearContentColor = CustomTheme.colors.white,
                disabledSelectedYearContentColor = CustomTheme.colors.gray,
                selectedYearContainerColor = CustomTheme.colors.main,
                disabledSelectedYearContainerColor = CustomTheme.colors.grayLight,
                dayContentColor = CustomTheme.colors.black,
                disabledDayContentColor = CustomTheme.colors.gray,
                selectedDayContentColor = CustomTheme.colors.white,
                disabledSelectedDayContentColor = CustomTheme.colors.gray,
                selectedDayContainerColor = CustomTheme.colors.main,
                disabledSelectedDayContainerColor = CustomTheme.colors.grayLight,
                todayContentColor = CustomTheme.colors.main,
                todayDateBorderColor = CustomTheme.colors.main,
                dayInSelectionRangeContentColor = CustomTheme.colors.white,
                dayInSelectionRangeContainerColor = CustomTheme.colors.main,
                dividerColor = CustomTheme.colors.black,
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = CustomTheme.colors.black,
                    unfocusedTextColor = CustomTheme.colors.gray,
                    disabledTextColor = CustomTheme.colors.grayLight,
                    errorTextColor = CustomTheme.colors.black,

                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,

                    focusedLabelColor = CustomTheme.colors.gray,
                    unfocusedLabelColor = CustomTheme.colors.gray,
                    disabledLabelColor = CustomTheme.colors.grayLight,
                    errorLabelColor = CustomTheme.colors.errorRed,

                    cursorColor = CustomTheme.colors.black,
                    errorCursorColor = CustomTheme.colors.black,

                    focusedIndicatorColor = CustomTheme.colors.gray,
                    unfocusedIndicatorColor = CustomTheme.colors.gray,
                    disabledIndicatorColor = CustomTheme.colors.grayLight,
                    errorIndicatorColor = CustomTheme.colors.errorRed,

                    focusedTrailingIconColor = CustomTheme.colors.gray,
                    unfocusedTrailingIconColor = CustomTheme.colors.gray,
                    disabledTrailingIconColor = CustomTheme.colors.grayLight,
                    errorTrailingIconColor = CustomTheme.colors.errorRed,

                    focusedPlaceholderColor = CustomTheme.colors.gray,
                    unfocusedPlaceholderColor = CustomTheme.colors.gray,
                    disabledPlaceholderColor = CustomTheme.colors.grayLight,
                    errorPlaceholderColor = CustomTheme.colors.errorRed,

                    disabledSupportingTextColor = CustomTheme.colors.errorRed,
                    errorSupportingTextColor = CustomTheme.colors.errorRed,
                )
            )
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