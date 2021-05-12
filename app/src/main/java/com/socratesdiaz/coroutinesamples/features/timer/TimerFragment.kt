package com.socratesdiaz.coroutinesamples.features.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.socratesdiaz.coroutinesamples.databinding.FragmentTimerBinding
import com.socratesdiaz.coroutinesamples.features.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.instance
import org.kodein.di.newInstance

class TimerFragment : BaseFragment() {
    private var binding: FragmentTimerBinding? = null
    private val viewModel: TimerViewModel by newInstance { TimerViewModel(instance()) }
    private var timerJob: Job? = null

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showTimer()
    }

    override fun onStart() {
        super.onStart()
        timerJob = lifecycleScope.launch {
            viewModel.timerFlow.collect {
                binding?.timerDisplay?.text = it
            }
        }
    }

    override fun onStop() {
        timerJob?.cancel()
        super.onStop()
    }
}