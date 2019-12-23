package com.dvidal.samplearticles.core.datasource.remote

import android.content.Context
import com.dvidal.samplearticles.core.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author diegovidal on 26/12/18.
 */

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}