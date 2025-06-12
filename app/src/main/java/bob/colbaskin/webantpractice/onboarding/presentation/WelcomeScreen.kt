package bob.colbaskin.webantpractice.onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.design_system.CustomOutlinedButton
import bob.colbaskin.webantpractice.common.design_system.FilledButton
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.navigation.Screens

@Composable
fun WelcomeScreenRoot(
    navController: NavHostController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    WelcomeScreen { action ->
        when (action) {
            WelcomeAction.CreateAccount -> {
                viewModel.onAction(WelcomeAction.OnboardingComplete)
                navController.navigate(Screens.SignUp) {
                    popUpTo(Screens.Welcome) { inclusive = true }
                }
            }
            WelcomeAction.HaveAccount -> {
                viewModel.onAction(WelcomeAction.OnboardingComplete)
                navController.navigate(Screens.SignIn) {
                    popUpTo(Screens.Welcome) { inclusive = true }
                }
            }
            else -> Unit
        }
        viewModel.onAction(action)
    }
}

@Composable
private fun WelcomeScreen(onAction: (WelcomeAction) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp),
            modifier = Modifier.padding(horizontal = 29.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.webant_logo),
                contentDescription = stringResource(R.string.webant_logo_description)
            )
            Text(
                text = stringResource(R.string.welcome_to_gallery),
                style = CustomTheme.typography.h1
            )
        }
        Spacer(modifier = Modifier.height(80.dp))
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            FilledButton(
                text = stringResource(R.string.create_account),
                onClick = { onAction(WelcomeAction.CreateAccount) },
                modifier = Modifier.fillMaxWidth()

            )
            CustomOutlinedButton(
                text = stringResource(R.string.have_account),
                onClick = { onAction(WelcomeAction.HaveAccount) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomeScreenPreview() {
    WebAntPracticeTheme { WelcomeScreen {} }
}