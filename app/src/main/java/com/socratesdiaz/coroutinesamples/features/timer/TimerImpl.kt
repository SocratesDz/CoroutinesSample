package com.socratesdiaz.coroutinesamples.features.timer

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@ExperimentalCoroutinesApi
class TimerImpl(private val coroutineScope: CoroutineScope) : Timer {
    private val tickDuration = 100L
    private var timerState: TimerState = Stopped
    private var eventChannel = Channel<TimerEvent>()

    override fun start() {
        coroutineScope.launch {
            eventChannel.send(StartTimer)
        }
    }

    override fun pause() {
        coroutineScope.launch {
            eventChannel.send(PauseTimer)
        }
    }

    override fun stop() {
        coroutineScope.launch {
            eventChannel.send(StopTimer)
        }
    }

    override fun reset() {
        coroutineScope.launch {
            eventChannel.send(ResetTimer)
        }
    }

    override fun isStarted() = timerState == Started

    override fun isStopped() = timerState == Stopped

    override fun isPaused() = timerState == Paused

    override fun getTime(): Flow<Long> {
        return coroutineScope.produce {
            var time = 0L
            while (coroutineScope.isActive) {
                withContext(Dispatchers.Default) {
                    when (eventChannel.poll()) {
                        is StartTimer -> {
                            timerState = Started
                        }
                        is PauseTimer -> {
                            timerState = Paused
                        }
                        is StopTimer -> {
                            timerState = Stopped
                        }
                        is ResetTimer -> {
                            timerState = Stopped
                            time = 0L
                        }
                        else -> {
                        }
                    }
                }

                withContext(Dispatchers.Default) {
                    when (timerState) {
                        Started -> {
                            send(time)
                            time += tickDuration
                            delay(tickDuration)
                        }
                        else -> {
                            send(time)
                        }
                    }
                }
            }
        }.receiveAsFlow()
    }
}

sealed class TimerEvent
object StartTimer : TimerEvent()
object PauseTimer : TimerEvent()
object StopTimer : TimerEvent()
object ResetTimer : TimerEvent()

sealed class TimerState
object Started : TimerState()
object Paused : TimerState()
object Stopped : TimerState()