package bob.colbaskin.webantpractice.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.common.user_prefs.domain.UserPreferencesRepository
import bob.colbaskin.webantpractice.common.user_prefs.data.models.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: UserPreferencesRepository): ViewModel() {
    val uiState: StateFlow<UiState<UserPreferences>> = repository.getUserPreferences().map {
        UiState.Success(it)
    }.distinctUntilChanged().stateIn(
        scope = viewModelScope,
        initialValue = UiState.Loading,
        started = SharingStarted.Companion.WhileSubscribed(5_000)
    )
}