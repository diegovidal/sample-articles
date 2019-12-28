package com.dvidal.samplearticles.core.common
import com.dvidal.samplearticles.core.datasource.remote.NetworkHandler
import com.dvidal.samplearticles.core.datasource.remote.RemoteFailure
import retrofit2.Call

/**
 * @author diegovidal on 26/12/18.
 */
abstract class BaseRequester(
        private val networkHandler: NetworkHandler
) {

    suspend fun <T, R> request(apiCall: suspend() -> T, transform: (T) -> R, default: T): EitherResult<R> {
        return when (networkHandler.isConnected) {
            true -> requestHttp(apiCall, transform, default)
            false, null -> EitherResult.left(RemoteFailure.NetworkConnection())
        }
    }

    private suspend fun <T, R> requestHttp(apiCall: suspend() -> T, transform: (T) -> R, default: T): EitherResult<R> {
        return try {
            val response = apiCall.invoke()
            EitherResult.right(transform((response ?: default)))
        } catch (exception: Throwable) {
            EitherResult.left(RemoteFailure.ServerError())
        }
    }
}