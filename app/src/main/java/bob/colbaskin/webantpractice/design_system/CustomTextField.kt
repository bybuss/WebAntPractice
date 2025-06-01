package bob.colbaskin.webantpractice.design_system

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.design_system.utils.clickableWithoutRipple
import bob.colbaskin.webantpractice.design_system.utils.convertMillisToDate
import bob.colbaskin.webantpractice.design_system.utils.getOutlinedColors

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
    modifier: Modifier = Modifier,
    text: String? = null,
    type: TextFieldType,
    selectedDate: Long? = null,
    onValueChange: (String) -> Unit = {},
    onDateSelected: (Long?) -> Unit = {},
    isRequired: Boolean = false,
    enabled: Boolean = true,
    isError: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var showPassword by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        Popup(
            onDismissRequest =  { showDatePicker = false },
            alignment = Alignment.TopStart
        ) {
            DatePickerModal(
                onDateSelected = { date ->
                    onDateSelected(date)
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }

    OutlinedTextField(
        value =  selectedDate?.let { convertMillisToDate(it) } ?: text ?: "",
        onValueChange = onValueChange,
        isError = isError,
        shape = CustomTheme.shapes.textField,
        modifier = if (type == TextFieldType.Birthday) {
            modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val up = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (up != null) showDatePicker = true
                    }
                }
        } else {
            modifier.fillMaxWidth()
        },
        enabled = enabled,
        readOnly = type == TextFieldType.Birthday,
        textStyle = CustomTheme.typography.p,
        placeholder = {
            Text(
                text = buildAnnotatedString {
                    append(
                        stringResource(
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
                    if (isRequired) {
                        withStyle(
                            style = SpanStyle(
                                color = CustomTheme.colors.main
                            )
                        ) {
                            append(" *")
                        }
                    }
                }
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
                            onClick = { showPassword = !showPassword },
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
        colors = TextFieldDefaults.getOutlinedColors()
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
            CustomTextField(
                text = "",
                type = TextFieldType.UserName,
                isRequired = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BirthdayPreview() {
    var selectedDate by remember { mutableStateOf<Long?>(null) }

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
                    enabled = enabled,
                    isError = isError
                )
                Spacer(Modifier.height(8.dp))
            }
            CustomTextField(
                selectedDate = selectedDate,
                type = TextFieldType.Birthday,
                onDateSelected = { newDate -> selectedDate = newDate },
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