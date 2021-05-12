package com.socratesdiaz.coroutinesamples

import com.socratesdiaz.coroutinesamples.features.timer.TimerViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore("Must find a way to run coroutine tests for hot streams.")
@ExperimentalCoroutinesApi
class TimerViewModelTest {
    private lateinit var viewModel: TimerViewModel
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Before
    fun setup() {
        viewModel = TimerViewModel()
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun clean() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Start timer`() = runBlockingTest {
        val values = mutableListOf<Long>()
        viewModel.showTimer()

        val job = launch {
            advanceTimeBy(2000L)
            viewModel.timer.take(2).collect { values.add(it) }
        }

        assertEquals(2000L, values.last())

        job.cancel()
    }

    @Test
    fun `Pause timer`() {

    }

    @Test
    fun `Stop timer`() {

    }
}