package bob.colbaskin.webantpractice.home.presentation.home

import androidx.paging.PagingData
import bob.colbaskin.webantpractice.home.domain.models.Photo
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val selectedIndex: Int = 0,
    val photos: Map<Int, Flow<PagingData<Photo>>> = emptyMap(),
    val searchQuery: String = "",
    val searchResults: List<Photo> = emptyList()
)
