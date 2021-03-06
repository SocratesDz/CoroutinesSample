package com.socratesdiaz.coroutinesamples.features.clock

import kotlinx.coroutines.flow.SharedFlow

interface Clock {
    fun getTimeFlow(): SharedFlow<Long>
}