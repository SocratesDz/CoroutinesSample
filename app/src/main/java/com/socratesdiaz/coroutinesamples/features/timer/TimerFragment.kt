package com.socratesdiaz.coroutinesamples.features.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.socratesdiaz.coroutinesamples.databinding.FragmentTimerBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TimerFragment : Fragment() {
    private var binding: FragmentTimerBinding? = null
    private lateinit var viewModel: TimerViewModel
    private var timerJob: Job? = null

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = TimerViewModel(TimerImpl(lifecycleScope))

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