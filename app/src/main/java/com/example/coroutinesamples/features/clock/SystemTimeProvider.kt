package com.example.coroutinesamples.features.clock

interface SystemTimeProvider {
    fun getCurrentSystemTime(): Long
}