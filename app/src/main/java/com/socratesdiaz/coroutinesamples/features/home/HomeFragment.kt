package com.socratesdiaz.coroutinesamples.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.socratesdiaz.coroutinesamples.R
import com.socratesdiaz.coroutinesamples.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.clockButton?.setOnClickListener {
            navigateToClockScreen()
        }
        binding?.timerButton?.setOnClickListener {
            navigateToTimerScreen()
        }
        binding?.foodFactoryButton?.setOnClickListener {
            navigateToFoodFactoryScreen()
        }
        binding?.apiFetchingButton?.setOnClickListener {
            navigateToApiFetchingScreen()
        }
    }

    private fun navigateToClockScreen() {
        findNavController().navigate(R.id.action_HomeFragment_to_clockFragment)
    }

    private fun navigateToTimerScreen() {
        findNavController().navigate(R.id.action_HomeFragment_to_timerFragment)
    }

    private fun navigateToFoodFactoryScreen() {

    }

    private fun navigateToApiFetchingScreen() {
        findNavController().navigate(R.id.action_HomeFragment_to_postListFragment)
    }
}