package bob.colbaskin.webantpractice.common.design_system

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme
import kotlinx.coroutines.launch

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    isIndicatorOnly: Boolean = false,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column {
            CircularProgressIndicator(
                color = CustomTheme.colors.gray,
                modifier = Modifier
                    .size(40.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                ,
                strokeWidth = 2.dp
            )
            if (!isIndicatorOnly) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.loading),
                    style = CustomTheme.typography.p,
                    color = CustomTheme.colors.gray,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun ErrorIndicator(
    title: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.webant_error_logo),
                contentDescription = stringResource(R.string.webant_error_logo)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                style = CustomTheme.typography.p,
                color = CustomTheme.colors.gray,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = text,
                style = CustomTheme.typography.caption,
                color = CustomTheme.colors.gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = {data ->
            CustomSnackbarContent(snackbarData = data)
        }
    )
}

@Composable
private fun CustomSnackbarContent(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(16.dp),
        shape = CustomTheme.shapes.snackbar,
        color = CustomTheme.colors.snackbarContainer
    ) {
        Row(
            modifier = modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.info),
                contentDescription = stringResource(R.string.info_logo_description),
                modifier = Modifier.size(20.dp),
                tint = CustomTheme.colors.white
            )
            Text(
                text = snackbarData.visuals.message,
                modifier = Modifier.weight(1f),
                color = CustomTheme.colors.white,
                style = CustomTheme.typography.h3,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingIndicatorPreview() {
    WebAntPracticeTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(20.dp))
            LoadingIndicator()
            Spacer(modifier = Modifier.size(20.dp))
            ErrorIndicator(
                title = stringResource(R.string.error_title),
                text = stringResource(R.string.error_text)
            )
            Spacer(modifier = Modifier.size(20.dp))
            Box(modifier = Modifier.size(360.dp, 200.dp)) {
                CustomSnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(R.string.toast_photo_successfully)
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    Text("Show Snackbar")
                }
            }
        }
    }
}