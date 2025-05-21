package bob.colbaskin.webantpractice.design_system

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.design_system.theme.WebAntPracticeTheme

@Composable
fun LoadingIndicator() {
    Box(contentAlignment = Alignment.Center) {
        Column {
            CircularProgressIndicator(
                color = CustomTheme.colors.gray,
                modifier = Modifier
                    .size(40.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                ,
                strokeWidth = 2.dp
            )
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

@Composable
fun ErrorIndicator(@StringRes title: Int, @StringRes text: Int) {
    Box(contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.webant_error_logo),
                contentDescription = stringResource(R.string.webant_error_logo)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(title),
                style = CustomTheme.typography.p,
                color = CustomTheme.colors.gray,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(text),
                style = CustomTheme.typography.caption,
                color = CustomTheme.colors.gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingIndicatorPreview() {
    WebAntPracticeTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(20.dp))
            LoadingIndicator()
            Spacer(modifier = Modifier.size(20.dp))
            ErrorIndicator(
                title = R.string.error_title,
                text = R.string.error_text
            )
        }
    }
}