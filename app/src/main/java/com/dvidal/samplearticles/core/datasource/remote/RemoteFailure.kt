package com.dvidal.samplearticles.core.datasource.remote

/**
 * @author diegovidal on 26/12/18.
 */
sealed class RemoteFailure(errorMsg: String) : Throwable(errorMsg) {

    class NetworkConnection : RemoteFailure("Network Connection Error")
    class ServerError : RemoteFailure("Server Error")

    class ErrorLoadingData : RemoteFailure("Error Loading Data")
}