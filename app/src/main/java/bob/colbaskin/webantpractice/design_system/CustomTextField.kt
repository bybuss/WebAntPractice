package bob.colbaskin.webantpractice.design_system

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.design_system.utils.clickableWithoutRipple
import bob.colbaskin.webantpractice.design_system.utils.convertMillisToDate
import bob.colbaskin.webantpractice.design_system.utils.toDate

enum class TextFieldType {
    UserName,
    Birthday,
    PhoneNumber,
    Email,
    Password,
    Empty
}

@Composable
fun CustomTextField(
    text: String? = null,
    type: TextFieldType,
    selectedDate: Long? = null,
    onValueChange: (String) -> Unit,
    onDateSelected: (Long?) -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var showPassword by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var internalText by remember { mutableStateOf("") }

    LaunchedEffect(selectedDate, text) {
        internalText = selectedDate?.let { convertMillisToDate(it) } ?: text ?: ""
    }

    if (showDatePicker) {
        Popup(
            onDismissRequest =  { showDatePicker = false },
            alignment = Alignment.TopStart
        ) {
            DatePickerModal(
                onDateSelected = { dateMillis ->
                    onDateSelected(dateMillis)
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }

    OutlinedTextField(
        value =  internalText,
        onValueChange = {
            internalText = it
            if (type == TextFieldType.Birthday) {
                onDateSelected(it.toDate().time)
                onValueChange(it)
            } else {
                onValueChange(it)
            }
        },
        isError = isError,
        shape = CustomTheme.shapes.textField,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        textStyle = CustomTheme.typography.p,
        placeholder = {
            Text(
                text = stringResource(
                    id = when (type) {
                        TextFieldType.UserName -> R.string.placeholder_user_name
                        TextFieldType.Birthday -> R.string.placeholder_birthday
                        TextFieldType.PhoneNumber -> R.string.placeholder_phone_number
                        TextFieldType.Email -> R.string.placeholder_email
                        TextFieldType.Password -> R.string.placeholder_password
                        TextFieldType.Empty -> R.string.placeholder_empty
                    }
                )
            )
        },
        trailingIcon = {
            if (type != TextFieldType.Empty) {
                Icon(
                    painter = painterResource(
                        id = when (type) {
                            TextFieldType.UserName -> R.drawable.person
                            TextFieldType.Birthday -> R.drawable.calendar
                            TextFieldType.PhoneNumber -> R.drawable.phone
                            TextFieldType.Password -> {
                                if (showPassword) R.drawable.eye_on
                                else R.drawable.eye_off
                            }
                            else -> R.drawable.mail
                        }
                    ),
                    contentDescription = stringResource(
                        id = when (type) {
                            TextFieldType.UserName -> R.string.user_name_logo_description
                            TextFieldType.Birthday -> R.string.birthday_logo_description
                            TextFieldType.PhoneNumber -> R.string.phone_number_logo_description
                            TextFieldType.Password -> R.string.password_logo_description
                            else -> R.string.email_logo_description
                        }
                    ),
                    modifier = if (type == TextFieldType.Password) {
                        Modifier.clickableWithoutRipple(
                            interactionSource = interactionSource,
                            onClick = { !showPassword },
                            enabled = enabled
                        )
                    } else if (type == TextFieldType.Birthday) {
                        Modifier.clickableWithoutRipple(
                            interactionSource = interactionSource,
                            onClick = { !showDatePicker },
                            enabled = enabled
                        )
                    } else {
                        Modifier
                    }
                )
            }
        },
        visualTransformation = when {
            type == TextFieldType.Password && !showPassword -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = when (type) {
                TextFieldType.UserName -> KeyboardType.Text
                TextFieldType.Birthday -> KeyboardType.Number
                TextFieldType.PhoneNumber -> KeyboardType.Phone
                TextFieldType.Email -> KeyboardType.Email
                TextFieldType.Password -> KeyboardType.Password
                TextFieldType.Empty -> KeyboardType.Text
            }
        ),
        supportingText = {
            if (isError) Text(
                text = stringResource(
                    id = when (type) {
                        TextFieldType.UserName -> R.string.error_user_name
                        TextFieldType.Birthday -> R.string.error_birthday
                        TextFieldType.PhoneNumber -> R.string.error_phone_number
                        TextFieldType.Email -> R.string.error_email
                        TextFieldType.Password -> R.string.error_password
                        TextFieldType.Empty -> R.string.error_empty_field
                    }
                )
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = CustomTheme.colors.black,
            unfocusedTextColor = CustomTheme.colors.gray,
            disabledTextColor = CustomTheme.colors.grayLight,
            errorTextColor = CustomTheme.colors.black,

            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,

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
}

@Preview(showBackground = true)
@Composable
private fun UserNamePreview() {
    WebAntPracticeTheme {
        Column(Modifier.padding(16.dp)) {
            listOf(
                Triple("JohnDoe", true, false),
                Triple("JohnDoe", false, false),
                Triple("фи", true, true),
                Triple("", false, true)
            ).forEach { (text, enabled, isError) ->
                CustomTextField(
                    text = text,
                    type = TextFieldType.UserName,
                    onValueChange = {},
                    enabled = enabled,
                    isError = isError
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BirthdayPreview() {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var text by remember { mutableStateOf("") }

    WebAntPracticeTheme {
        Column(Modifier.padding(16.dp)) {
            listOf(
                Triple("12.31.1990", true, false),
                Triple("", false, false),
                Triple("12./31/1990", true, true),
                Triple("", false, true)
            ).forEach { (text, enabled, isError) ->
                CustomTextField(
                    text = text,
                    type = TextFieldType.Birthday,
                    onValueChange = {},
                    enabled = enabled,
                    isError = isError
                )
                Spacer(Modifier.height(8.dp))
            }
            CustomTextField(
                selectedDate = selectedDate,
                type = TextFieldType.Birthday,
                onValueChange = { text = it },
                onDateSelected = { dateMillis ->
                    selectedDate = dateMillis
                },
                enabled = true,
                isError = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhoneNumberPreview() {
    WebAntPracticeTheme {
        Column(Modifier.padding(16.dp)) {
            listOf(
                Triple("+1234567890", true, false),
                Triple("", false, false),
                Triple("123", true, true),
                Triple("", true, true)
            ).forEach { (text, enabled, isError) ->
                CustomTextField(
                    text = text,
                    type = TextFieldType.PhoneNumber,
                    onValueChange = {},
                    enabled = enabled,
                    isError = isError
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmailPreview() {
    WebAntPracticeTheme {
        Column(Modifier.padding(16.dp)) {
            listOf(
                Triple("test@example.com", true, false),
                Triple("", false, false),
                Triple("invalid-email", true, true),
                Triple("", false, true)
            ).forEach { (text, enabled, isError) ->
                CustomTextField(
                    text = text,
                    type = TextFieldType.Email,
                    onValueChange = {},
                    enabled = enabled,
                    isError = isError
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordPreview() {
    var pswd = remember { mutableStateOf("") }
    WebAntPracticeTheme {
        Column(Modifier.padding(16.dp)) {
            listOf(
                Triple("Password1!", true, false),
                Triple("", false, false),
                Triple("weak", true, true),
                Triple("", true, true)
            ).forEach { (text, enabled, isError) ->
                CustomTextField(
                    text = text,
                    type = TextFieldType.Password,
                    onValueChange = {},
                    enabled = enabled,
                    isError = isError
                )
                Spacer(Modifier.height(8.dp))
            }
            CustomTextField(
                text = pswd.value,
                type = TextFieldType.Password,
                onValueChange = { pswd.value = it },
                enabled = true,
                isError = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyPreview() {
    WebAntPracticeTheme {
        Column(Modifier.padding(16.dp)) {
            listOf(
                Triple("Text", true, false),
                Triple("", false, false),
                Triple("Invalid", true, true),
                Triple("", true, true)
            ).forEach { (text, enabled, isError) ->
                CustomTextField(
                    text = text,
                    type = TextFieldType.Empty,
                    onValueChange = {},
                    enabled = enabled,
                    isError = isError
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}