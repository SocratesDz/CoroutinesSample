package com.example.coroutinesamples


import com.example.coroutinesamples.features.clock.ClockImpl
import com.example.coroutinesamples.features.clock.SystemTimeProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class ClockTest {
    @Test
    fun `Return time correctly after 5 second passes`() = runBlockingTest {
        // Given
        val timeProvider = object : SystemTimeProvider {
            override fun getCurrentSystemTime(): Long = 0L
        }
        val coroutineScope = TestCoroutineScope()
        val clock = ClockImpl(coroutineScope = coroutineScope, systemTimeProvider = timeProvider)

        // Act
        val timeFlow = clock.getTimeFlow()
        advanceTimeBy(5000L)

        // Assert
        coroutineScope.launch {
            timeFlow.collectLatest {
                assertEquals(5000L, it)
            }
        }
    }
}