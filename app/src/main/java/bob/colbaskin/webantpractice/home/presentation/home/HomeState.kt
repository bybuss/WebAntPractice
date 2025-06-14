package bob.colbaskin.webantpractice.home.presentation.home

import androidx.paging.PagingData
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.home.domain.models.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class HomeState(
    val selectedIndex: Int = 0,
    val photos: Map<Int, Flow<PagingData<Photo>>> = emptyMap()
)
