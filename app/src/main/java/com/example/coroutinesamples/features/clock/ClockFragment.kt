package com.example.coroutinesamples.features.clock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.coroutinesamples.R
import com.example.coroutinesamples.databinding.FragmentClockBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class ClockFragment : Fragment() {
    private lateinit var binding: FragmentClockBinding
    private var uiStateJob: Job = Job()
    private lateinit var viewModel: ClockViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clock, container, false)
        viewModel = ClockViewModel(ClockImpl(lifecycleScope, SystemTimeProviderImpl()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.displayTime()
    }

    override fun onStart() {
        super.onStart()
        uiStateJob = lifecycleScope.launchWhenStarted {
            viewModel.timeDisplay.collect { time ->
                binding.clockDisplay.text = time
            }
        }
    }

    override fun onStop() {
        uiStateJob.cancel()
        super.onStop()
    }
}