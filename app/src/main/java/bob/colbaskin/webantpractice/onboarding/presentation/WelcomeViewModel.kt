package bob.colbaskin.webantpractice.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.webantpractice.common.user_prefs.domain.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val dataStore: UserPreferencesRepository
): ViewModel() {

    fun onAction(action: WelcomeAction) {
        when (action) {
            WelcomeAction.OnboardingComplete -> {
                viewModelScope.launch {
                    dataStore.saveOnboardingStatus(OnboardingConfig.COMPLETED)
                }
            }
            else -> Unit
        }
    }
}