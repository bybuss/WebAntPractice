package bob.colbaskin.webantpractice.common.design_system.utils

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import bob.colbaskin.webantpractice.common.design_system.TextButtonType
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme

@Composable
fun TextFieldDefaults.getOutlinedColors() = colors(
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

    focusedLeadingIconColor = CustomTheme.colors.gray,
    unfocusedLeadingIconColor = CustomTheme.colors.gray,
    disabledLeadingIconColor = CustomTheme.colors.grayLight,
    errorLeadingIconColor = CustomTheme.colors.errorRed,

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

@Composable
fun TextFieldDefaults.getColors() = colors(
    focusedTextColor = CustomTheme.colors.black,
    unfocusedTextColor = CustomTheme.colors.graySecondary,
    disabledTextColor = CustomTheme.colors.graySearchDisabled,
    errorTextColor = CustomTheme.colors.black,

    focusedContainerColor = CustomTheme.colors.graySecondary,
    unfocusedContainerColor = CustomTheme.colors.graySecondary,
    disabledContainerColor = CustomTheme.colors.graySecondary,
    errorContainerColor = CustomTheme.colors.graySecondary,

    focusedLabelColor = CustomTheme.colors.gray,
    unfocusedLabelColor = CustomTheme.colors.gray,
    disabledLabelColor = CustomTheme.colors.graySearchDisabled,
    errorLabelColor = CustomTheme.colors.errorRed,

    cursorColor = CustomTheme.colors.black,
    errorCursorColor = CustomTheme.colors.black,

    focusedIndicatorColor = CustomTheme.colors.black,
    unfocusedIndicatorColor = CustomTheme.colors.graySecondary,
    disabledIndicatorColor = CustomTheme.colors.graySearchDisabled,
    errorIndicatorColor = CustomTheme.colors.errorRed,

    focusedLeadingIconColor = CustomTheme.colors.graySecondary,
    unfocusedLeadingIconColor = CustomTheme.colors.graySecondary,
    disabledLeadingIconColor = CustomTheme.colors.graySearchDisabled,
    errorLeadingIconColor = CustomTheme.colors.graySecondary,

    focusedTrailingIconColor = CustomTheme.colors.graySecondary,
    unfocusedTrailingIconColor = CustomTheme.colors.graySecondary,
    disabledTrailingIconColor = CustomTheme.colors.graySearchDisabled,
    errorTrailingIconColor = CustomTheme.colors.graySecondary,

    focusedPlaceholderColor = CustomTheme.colors.graySecondary,
    unfocusedPlaceholderColor = CustomTheme.colors.graySecondary,
    disabledPlaceholderColor = CustomTheme.colors.graySearchDisabled,
    errorPlaceholderColor = CustomTheme.colors.errorRed,

    disabledSupportingTextColor = CustomTheme.colors.errorRed,
    errorSupportingTextColor = CustomTheme.colors.errorRed,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDefaults.getColors() = colors(
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
    dateTextFieldColors = TextFieldDefaults.getOutlinedColors()
)

@Composable
fun ButtonDefaults.getTextButtonColors(
    type: TextButtonType,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    isSelected: Boolean = false,
    isPressed: Boolean = false
) = textButtonColors(
    contentColor = when (type) {
      TextButtonType.Default -> {
          when {
              isLoading -> CustomTheme.colors.black
              !enabled -> CustomTheme.colors.grayLight
              isPressed -> CustomTheme.colors.main
              else -> CustomTheme.colors.black
          }
      }
      TextButtonType.Tab -> {
          when {
              !enabled -> CustomTheme.colors.grayLight
              isPressed -> CustomTheme.colors.main
              !isSelected -> CustomTheme.colors.gray
              else -> CustomTheme.colors.black
          }
      }
      TextButtonType.Pink -> CustomTheme.colors.main
    },
    disabledContentColor = CustomTheme.colors.gray
)
