package com.socratesdiaz.coroutinesamples.features.apifetch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.socratesdiaz.coroutinesamples.databinding.FragmentPostDetailBinding
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post
import com.socratesdiaz.coroutinesamples.features.base.BaseFragment

class PostDetailFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val post = arguments?.get("post") as Post
        val binding = FragmentPostDetailBinding.inflate(inflater)
        binding.post = post
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}