package com.socratesdiaz.coroutinesamples.features.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.isActive

@ExperimentalCoroutinesApi
class TimerImpl(private val coroutineScope: CoroutineScope): Timer {
    private var elapsedTime = 0L
    private var isStarted = false
    private var timeChannel: Flow<Long>? = null

    override fun start() {
        timeChannel = callbackFlow<Long> {
            while(coroutineScope.isActive) {
                    elapsedTime += 1000L
                    offer(elapsedTime)
            }
        }
    }

    override fun pause() {
        timeChannel?.cancellable()
    }

    override fun stop() {
        elapsedTime = 0L
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

    override fun isStarted() {
        TODO("Not yet implemented")
    }

    override fun isStopped() {
        TODO("Not yet implemented")
    }

    override fun getTime(): Flow<Long>? {
        return timeChannel
    }
}