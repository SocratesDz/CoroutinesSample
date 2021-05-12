package com.socratesdiaz.coroutinesamples.features.timer

import com.socratesdiaz.coroutinesamples.base.CoroutineDispatchers
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow

@ExperimentalCoroutinesApi
class TimerImpl(
    private val coroutineDispatchers: CoroutineDispatchers
) : Timer {
    private val tickDuration = 100L
    private var timerState: TimerState = Stopped
    private var eventChannel = Channel<TimerEvent>()

    override suspend fun start() {
        eventChannel.send(StartTimer)
    }

    override suspend fun stop() {
        eventChannel.send(StopTimer)
    }

    override suspend fun reset() {
        eventChannel.send(ResetTimer)
    }

    override fun isStarted() = timerState == Started

    override fun isStopped() = timerState == Stopped

    override suspend fun getTime(): Flow<Long> {
        return flow {
            var time = 0L
            while (true) {
                when (eventChannel.poll()) {
                    is StartTimer -> timerState = Started
                    is ResetTimer -> {
                        timerState = Stopped
                        time = 0L
                    }
                    is StopTimer -> timerState = Stopped
                    null -> {
                    }
                }
                if (timerState == Started) {
                    emit(time)
                    time += tickDuration
                    delay(tickDuration)
                }
            }
        }.flowOn(coroutineDispatchers.default)
    }

    override fun dispose() {
        eventChannel.close()
    }
}

sealed class TimerEvent
object StartTimer : TimerEvent()
object StopTimer : TimerEvent()
object ResetTimer : TimerEvent()

sealed class TimerState
object Started : TimerState()
object Stopped : TimerState()