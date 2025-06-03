package bob.colbaskin.webantpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import bob.colbaskin.webantpractice.common.MainViewModel
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.theme.WebAntPracticeTheme
import bob.colbaskin.webantpractice.common.user.models.UserPreferences
import bob.colbaskin.webantpractice.navigation.AppNavHost
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState: UiState<UserPreferences> by mutableStateOf(UiState.Loading)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState
                    .onEach { uiState = it }
                    .collect {}
            }
        }

        enableEdgeToEdge()
        setContent {
            WebAntPracticeTheme {
                if (uiState is UiState.Success<UserPreferences>) {
                    AppNavHost(uiState = uiState as UiState.Success<UserPreferences>)
                }
            }
        }
    }
}