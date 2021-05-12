package com.socratesdiaz.coroutinesamples.features.apifetch.datasource

interface ConnectivityProvider {
    fun isConnected(): Boolean
}