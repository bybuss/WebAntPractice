package bob.colbaskin.webantpractice.add_photo.presentation.add_data

import android.net.Uri
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.home.domain.models.FullPhoto

data class AddDataState(
    val image: Uri = Uri.EMPTY,
    val name: String = "",
    val description: String = "",
    val isNew: Boolean = true,
    val isPopular: Boolean = false,
    val isLoading: Boolean = false,
    val isAddButtonEnabled: Boolean = false,
    val fullPhoto: UiState<FullPhoto> = UiState.Loading
) {
    val isConfirmButtonEnabled: Boolean
        get() = name.isNotEmpty() && description.isNotEmpty() && isNew || isPopular
}
