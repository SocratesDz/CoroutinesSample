package com.socratesdiaz.coroutinesamples.features.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.socratesdiaz.coroutinesamples.R
import com.socratesdiaz.coroutinesamples.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {
    private var binding: FragmentTimerBinding? = null
    private lateinit var viewModel: TimerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = TimerViewModel()

        binding = FragmentTimerBinding.inflate(inflater)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}