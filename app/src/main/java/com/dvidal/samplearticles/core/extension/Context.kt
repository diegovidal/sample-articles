package com.dvidal.samplearticles.core.extension

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author diegovidal on 26/12/18.
 */

val Context.connectivityManager: ConnectivityManager get() =
    this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
