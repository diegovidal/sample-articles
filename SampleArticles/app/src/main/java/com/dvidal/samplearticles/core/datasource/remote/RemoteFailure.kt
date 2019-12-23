package com.dvidal.samplearticles.core.datasource.remote

/**
 * @author diegovidal on 26/12/18.
 */
sealed class RemoteFailure {

    class NetworkConnection : Throwable()
    class ServerError : Throwable()
}