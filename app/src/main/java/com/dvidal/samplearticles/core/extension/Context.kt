package com.dvidal.samplearticles.core.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @author diegovidal on 26/12/18.
 */

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo