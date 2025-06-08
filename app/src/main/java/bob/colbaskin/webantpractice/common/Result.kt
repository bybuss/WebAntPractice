package bob.colbaskin.webantpractice.common

sealed interface Result <out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val title: String, val text: String) : Result<Nothing>
}

fun <T> Result<T>.toUiState(): UiState<T> = when (this) {
    is Result.Success -> UiState.Success(data = data)
    is Result.Error -> UiState.Error(title = title, text = text)
}
