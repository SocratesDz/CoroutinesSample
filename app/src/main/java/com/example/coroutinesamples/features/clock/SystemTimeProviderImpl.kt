package com.example.coroutinesamples.features.clock

class SystemTimeProviderImpl: SystemTimeProvider {
    override fun getCurrentSystemTime(): Long {
        return System.currentTimeMillis()
    }
}