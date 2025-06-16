package bob.colbaskin.webantpractice.common.design_system

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme

data class MenuItem(
    val text: String,
    val action: () -> Unit
)

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    menuItemData: List<MenuItem>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        containerColor = CustomTheme.colors.white,
    ) {
        menuItemData.forEach { item ->
            DropdownItem(
                text = item.text,
                onClick = item.action
            )
        }
    }
}

@Composable
fun DropdownItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    var isEnabled = remember { mutableStateOf(true) }.value

    DropdownMenuItem(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start
            )
        },
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier.background(
            when {
                isPressed -> CustomTheme.colors.main
                !isEnabled -> CustomTheme.colors.grayLight
                else -> CustomTheme.colors.white
            }
        ),
        colors = MenuDefaults.itemColors(
            textColor = when {
                isPressed -> CustomTheme.colors.white
                else -> CustomTheme.colors.black
            },
            disabledTextColor = CustomTheme.colors.gray
        ),
        interactionSource = interactionSource,
    )
}

@Preview(showBackground = true)
@Composable
private fun DropdownPreview() {
    var expanded by remember { mutableStateOf(false) }

    WebAntPracticeTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    painter = painterResource(R.drawable.more_vert),
                    contentDescription = stringResource(R.string.more_vert_logo_description)
                )
            }
            Dropdown(
                menuItemData = listOf(
                    MenuItem("Menu Item") { },
                    MenuItem("Menu Item") { },
                    MenuItem("Menu Item") { },
                    MenuItem("Menu Item") { },
                    MenuItem("Menu Item") { },
                ),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DropdownItemPreview() {
    WebAntPracticeTheme {
        DropdownItem(text = "Menu Item", onClick = {})
    }
}