package com.example.coroutinesamples.features.clock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ClockViewModel(private val clock: Clock): ViewModel() {
    private val _timeDisplay = MutableStateFlow("")
    val timeDisplay: StateFlow<String> = _timeDisplay

    private fun formatTime(time: Long): String {
        return SimpleDateFormat("hh:mm:ss", Locale.US).format(Date(time))
    }

    fun displayTime() {
        viewModelScope.launch {
            clock.getTimeFlow().map(::formatTime)
                .collect { _timeDisplay.value = it }
        }
    }
}