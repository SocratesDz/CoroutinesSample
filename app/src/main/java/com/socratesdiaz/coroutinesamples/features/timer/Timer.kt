package com.socratesdiaz.coroutinesamples.features.timer

import kotlinx.coroutines.flow.Flow

interface Timer {
    suspend fun start()
    suspend fun stop()
    suspend fun reset()
    fun isStarted(): Boolean
    fun isStopped(): Boolean
    suspend fun getTime(): Flow<Long>
    fun dispose()
}