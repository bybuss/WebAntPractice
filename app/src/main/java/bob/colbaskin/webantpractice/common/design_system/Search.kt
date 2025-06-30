package bob.colbaskin.webantpractice.common.design_system

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.common.design_system.utils.clickableWithoutRipple
import bob.colbaskin.webantpractice.common.design_system.utils.getColors
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(searchResults) {
        expanded = searchResults.isNotEmpty()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 375.dp)
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            windowInsets = WindowInsets(0.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = {
                        textFieldState.edit { replace(0, length, it) }
                        onSearch(it)
                    },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { newExpanded ->
                        if (!newExpanded) {
                            expanded = false
                        }
                    },
                    placeholder = { Text(
                        text = stringResource(R.string.search_placeholder)
                    ) },
                    leadingIcon = {
                        IconButton(onClick = {
                            onSearch(textFieldState.text.toString())
                            expanded = false
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.search),
                                contentDescription = stringResource(R.string.search_logo_description),
                            )
                        }
                    },
                    trailingIcon = {
                        if (textFieldState.text.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    textFieldState.clearText()
                                    onSearch(textFieldState.text.toString())
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.close),
                                    contentDescription = stringResource(R.string.close_logo_description),
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.getColors()
                )
            },
            expanded = expanded,
            onExpandedChange = { newExpanded ->
                if (!newExpanded) {
                    expanded = false
                }
            },
            shape = CustomTheme.shapes.search,
            colors = SearchBarDefaults.colors(
                containerColor = CustomTheme.colors.grayLight,
                dividerColor = CustomTheme.colors.graySecondary
            )
        ) {
            LazyColumn {
                items(items = searchResults, key = { UUID.randomUUID() }) { resultText ->
                    ListItem(
                        headlineContent = { Text(text = resultText) },
                        modifier = Modifier
                            .clickableWithoutRipple(
                                onClick = {
                                    textFieldState.edit { replace(0, length, resultText) }
                                    expanded = false
                                    onSearch(resultText)
                                }
                            )
                            .fillMaxWidth(),
                        colors = ListItemDefaults.colors(
                            containerColor = CustomTheme.colors.white,
                            headlineColor = CustomTheme.colors.black,
                            overlineColor = CustomTheme.colors.graySecondary,
                            disabledHeadlineColor = CustomTheme.colors.gray,
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    val textState = rememberTextFieldState("")
    var results by remember { mutableStateOf(listOf<String>()) }
    val wordsForSearch = listOf(
        "List item", "List item", "List item", "List item", "List item",
        "List item", "List item", "List item", "List item", "List item",
        "List iem", "List itm", "List tem", "Lst item", "Lis item",
        "List item", "List item", "List item", "List item", "List item"
    )

    WebAntPracticeTheme {
        Search(
            textFieldState = textState,
            onSearch = { query ->
                results = if (query.isNotEmpty()) {
                    wordsForSearch.filter {
                        it.startsWith(query, ignoreCase = true)
                    }
                } else {
                    emptyList()
                }
            },
            searchResults = results,
            modifier = Modifier.padding(16.dp)
        )
    }
}