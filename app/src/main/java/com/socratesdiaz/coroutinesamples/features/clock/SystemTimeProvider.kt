package com.socratesdiaz.coroutinesamples.features.clock

interface SystemTimeProvider {
    fun getCurrentSystemTime(): Long
}