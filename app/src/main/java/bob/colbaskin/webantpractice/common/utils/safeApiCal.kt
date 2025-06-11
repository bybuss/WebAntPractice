package bob.colbaskin.webantpractice.common.utils

import android.content.Context
import bob.colbaskin.webantpractice.common.Result
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import bob.colbaskin.webantpractice.R

suspend inline fun <reified  T, reified  R> safeApiCall(
    apiCall: suspend () -> T,
    successHandler: (T) -> R,
    context: Context
): Result<R> {
    return try {
        val response = apiCall()
        val result = successHandler(response)
        Result.Success(data = result)
    } catch (e: Exception) {
        when (e) {
            is IOException -> Result.Error(
                title = context.getString(R.string.network_error_title),
                text = e.message.toString()
            )
            is TimeoutCancellationException -> Result.Error(
                title = context.getString(R.string.timeout_error_title),
                text = e.message.toString()
            )
            is HttpException -> {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = errorBody ?: e.message().toString()

                when (e.code()) {
                    400 -> Result.Error(
                        title = context.getString(R.string.user_credentials_error_title),
                        text = context.getString(R.string.user_credentials_error_text)
                    )
                    401 -> Result.Error(
                        title = context.getString(R.string.authorization_error_title),
                        text = context.getString(R.string.authorization_error_text)
                    )
                    422 -> Result.Error(
                        title = context.getString(R.string.fields_error_title),
                        text = context.getString(R.string.fields_error_text)
                    )
                    in 500..599 -> Result.Error(
                        title = context.getString(R.string.server_error_title),
                        text = errorMessage
                    )
                    else -> Result.Error(
                        title = context.getString(R.string.error_title),
                        text = errorMessage
                    )
                }
            }
            else -> Result.Error(
                title = context.getString(bob.colbaskin.webantpractice.R.string.generic_error_title),
                text = e.message.toString()
            )
        }
    }
}
