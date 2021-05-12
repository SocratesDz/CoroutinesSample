package com.socratesdiaz.coroutinesamples.features.clock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.socratesdiaz.coroutinesamples.databinding.FragmentClockBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

class ClockFragment : Fragment() {
    private var binding: FragmentClockBinding? = null
    private var uiStateJob: Job = Job()
    private lateinit var viewModel: ClockViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ClockViewModel(ClockImpl(lifecycleScope, SystemTimeProviderImpl()))
        binding = FragmentClockBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.displayTime()
    }

    override fun onStart() {
        super.onStart()
        uiStateJob = lifecycleScope.launchWhenStarted {
            viewModel.timeDisplay.collect { time ->
                binding?.clockDisplay?.text = time
            }
        }
    }

    override fun onStop() {
        uiStateJob.cancel()
        super.onStop()
    }
}