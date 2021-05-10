package com.socratesdiaz.coroutinesamples.features.clock

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.isActive

class ClockImpl(private val coroutineScope: CoroutineScope, private val systemTimeProvider: SystemTimeProvider): Clock {
    private val oneSecond = 1000L

    override fun getTimeFlow(): SharedFlow<Long> = flow {
        var time = systemTimeProvider.getCurrentSystemTime()
        while(coroutineScope.isActive) {
            emit(time)
            time += oneSecond
            delay(oneSecond)
        }
    }.shareIn(
        coroutineScope,
        SharingStarted.Eagerly
    )
}