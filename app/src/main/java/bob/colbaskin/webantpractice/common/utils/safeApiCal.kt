package bob.colbaskin.webantpractice.common.utils

import android.content.Context
import bob.colbaskin.webantpractice.common.Result
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException

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
                title = context.getString(bob.colbaskin.webantpractice.R.string.network_error_title),
                text = e.message.toString()
            )
            is TimeoutCancellationException -> Result.Error(
                title = context.getString(bob.colbaskin.webantpractice.R.string.timeout_error_title),
                text = e.message.toString()
            )
            is HttpException -> {
                when (e.code()) {
                    400 -> Result.Error(
                        title = context.getString(bob.colbaskin.webantpractice.R.string.error_title),
                        text = e.message().toString()
                    )
                    401 -> Result.Error(
                        title = context.getString(bob.colbaskin.webantpractice.R.string.authorization_error_title),
                        text = context.getString(bob.colbaskin.webantpractice.R.string.authorization_error_text)
                    )
                    in 500..599 -> Result.Error(
                        title = context.getString(bob.colbaskin.webantpractice.R.string.server_error_title),
                        text = e.message().toString()
                    )
                    else -> Result.Error(
                        title = context.getString(bob.colbaskin.webantpractice.R.string.error_title),
                        text = e.message().toString()
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
