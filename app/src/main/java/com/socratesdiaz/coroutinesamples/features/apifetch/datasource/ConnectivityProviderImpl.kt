package com.socratesdiaz.coroutinesamples.features.apifetch.datasource

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityProviderImpl(private val context: Context): ConnectivityProvider {
    override fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}