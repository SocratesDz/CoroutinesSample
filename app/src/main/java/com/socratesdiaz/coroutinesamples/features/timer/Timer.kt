package com.socratesdiaz.coroutinesamples.features.timer

import kotlinx.coroutines.flow.Flow

interface Timer {
    fun start()
    fun pause()
    fun stop()
    fun reset()
    fun isStarted(): Boolean
    fun isStopped(): Boolean
    fun isPaused(): Boolean
    fun getTime(): Flow<Long>
}