package bob.colbaskin.webantpractice.common

sealed interface Result <out T> {
    data object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class Error(val title: String, val text: String) : Result<Nothing>
}
