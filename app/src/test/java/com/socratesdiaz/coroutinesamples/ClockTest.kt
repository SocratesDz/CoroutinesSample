package com.socratesdiaz.coroutinesamples


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.socratesdiaz.coroutinesamples.features.clock.ClockImpl
import com.socratesdiaz.coroutinesamples.features.clock.SystemTimeProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore("Must find a way to run coroutine tests for hot streams.")
@ExperimentalCoroutinesApi
class ClockTest {
    @get:Rule
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val coroutineScope = TestCoroutineScope(coroutineDispatcher)

    @Test
    fun `Return time correctly after 5 second passes`() = runBlockingTest {
        // Given
        val timeValues = mutableListOf<Long>()
        val timeProvider = object : SystemTimeProvider {
            override fun getCurrentSystemTime(): Long = 0L
        }

        val clockTestCoroutineDispatcher = TestCoroutineDispatcher()
        val clockTestCoroutineScope = TestCoroutineScope(clockTestCoroutineDispatcher)

        val clock =
            ClockImpl(coroutineScope = coroutineScope, systemTimeProvider = timeProvider)
        val timeFlow = clock.getTimeFlow()
        // Act
        withTimeoutOrNull(5000L) {
            advanceTimeBy(5000L)
            val returned = timeFlow.first()
            assertEquals(5000L, returned)
        }
    }
}