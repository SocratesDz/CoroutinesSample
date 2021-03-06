package com.socratesdiaz.coroutinesamples

import com.socratesdiaz.coroutinesamples.features.clock.Clock
import com.socratesdiaz.coroutinesamples.features.clock.ClockViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Ignore("Must find a way to run coroutine tests for hot streams.")
@ExperimentalCoroutinesApi
class ClockViewModelTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun clean() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Show current time formatted`() = runBlockingTest {
        // Given
        val clock = mock(Clock::class.java)
        `when`(clock.getTimeFlow()).thenReturn(
            flow { emit(2300L) }.shareIn(
                this,
                SharingStarted.Eagerly
            )
        )

        // Act
        val viewModel = ClockViewModel(clock)
        viewModel.displayTime()

        // Assert
        testCoroutineScope.launch {
            viewModel.timeDisplay.collectLatest {
                assertEquals("00:00:02", it)
            }
        }
    }
}