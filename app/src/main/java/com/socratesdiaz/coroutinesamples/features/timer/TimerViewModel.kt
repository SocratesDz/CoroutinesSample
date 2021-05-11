package com.socratesdiaz.coroutinesamples.features.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TimerViewModel(private val timer: Timer) : ViewModel() {
    private var _timerFlow = MutableStateFlow("00:00:00")
    var timerFlow = _timerFlow.asStateFlow()

    fun showTimer() {
        viewModelScope.launch {
            timer.getTime().map(::formatTime)
                .collect { _timerFlow.value = it }

        }
    }

    fun startPauseTimer() {
        if(timer.isStarted()) {
            timer.pause()
        } else {
            timer.start()
        }
    }

    fun stopTimer() {
        if(timer.isStarted()) {
            timer.stop()
        } else if(timer.isStopped()) {
            timer.reset()
        }
    }

    private fun formatTime(time: Long): String {
        val seconds = time / 1000
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60))
    }
}