package com.socratesdiaz.coroutinesamples.features.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private var _timer = MutableStateFlow(0L)
    var timer: StateFlow<Long> = _timer

    fun startTimer() {
        viewModelScope.launch {
            flow {
                var seconds = 0L
                while (currentCoroutineContext().isActive) {
                    emit(seconds)
                    delay(1000L)
                    seconds += 1000L
                }
            }.collect { currentTime ->
                _timer.value = currentTime
            }
        }
    }

}