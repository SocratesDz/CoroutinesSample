package com.socratesdiaz.coroutinesamples.features.clock

class SystemTimeProviderImpl: SystemTimeProvider {
    override fun getCurrentSystemTime(): Long {
        return System.currentTimeMillis()
    }
}