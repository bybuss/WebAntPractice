package bob.colbaskin.webantpractice.home.presentation.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import bob.colbaskin.webantpractice.home.data.PhotoPagingSource
import bob.colbaskin.webantpractice.home.domain.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        updateSelectedIndex(0)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.TabSelected -> updateSelectedIndex(action.index)
            else -> null
        }
    }

    private fun updateSelectedIndex(selectedIndex: Int) {
        val existingPager = state.photos[selectedIndex]
        if (existingPager != null) {
            state = state.copy(
                selectedIndex = selectedIndex,
                photos = state.photos
            )
        } else {
            val newPager = Pager(PagingConfig(pageSize = 20)) {
                PhotoPagingSource(
                    context = context,
                    photosRepository = photosRepository,
                    new = if (selectedIndex == 0) true else null,
                    popular = if (selectedIndex == 1) true else null
                )
            }.flow.cachedIn(viewModelScope)

            state = state.copy(
                selectedIndex = selectedIndex,
                photos = state.photos + (selectedIndex to newPager)
            )
        }
    }
}
