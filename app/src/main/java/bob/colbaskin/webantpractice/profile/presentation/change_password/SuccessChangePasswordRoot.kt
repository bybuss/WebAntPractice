package bob.colbaskin.webantpractice.profile.presentation.change_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.CenteredTextTopAppBar
import bob.colbaskin.webantpractice.common.design_system.FilledButton
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.navigation.Screens

@Composable
fun SuccessChangePasswordRoot(
    navController: NavHostController,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { CenteredTextTopAppBar(title = stringResource(R.string.top_bar_title_new_password)) },
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        SuccessChangePasswordScreen(
            onAction = { action ->
                when (action) {
                    ChangePasswordAction.ReturnToProfile -> {
                        navController.navigate(Screens.Profile) {
                            popUpTo(Screens.SuccessChangePassword) { inclusive = true}
                        }
                    }
                    else -> Unit
                }
                viewModel.onAction(action)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun SuccessChangePasswordScreen(
    onAction: (ChangePasswordAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 28.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.gradient_check_mark),
            contentDescription = stringResource(R.string.check_mark_logo_description)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = stringResource(R.string.congratulations),
                style = CustomTheme.typography.h1,
                color = CustomTheme.colors.black,
            )
            Text(
                text = stringResource(R.string.password_successfully_changed),
                style = CustomTheme.typography.p,
                color = CustomTheme.colors.black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        FilledButton(
            text = stringResource(R.string.return_to_profile),
            onClick = { onAction(ChangePasswordAction.ReturnToProfile) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
