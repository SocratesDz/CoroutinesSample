package com.example.coroutinesamples.features.clock

import kotlinx.coroutines.flow.SharedFlow

interface Clock {
    fun getTimeFlow(): SharedFlow<Long>
}