package com.socratesdiaz.coroutinesamples.features.apifetch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.socratesdiaz.coroutinesamples.databinding.FragmentPostListBinding
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post
import com.socratesdiaz.coroutinesamples.features.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.direct
import org.kodein.di.instance

class PostListFragment : BaseFragment() {
    private lateinit var viewModel: PostViewModel
    private lateinit var binding: FragmentPostListBinding
    private lateinit var adapter: PostAdapter
    private var errorJob: Job? = null

    companion object {
        private val TAG = PostListFragment::class.java.simpleName
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = PostViewModel(di.direct.instance(), di.direct.instance())
        adapter = PostAdapter(::goToDetail)
        binding = FragmentPostListBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun goToDetail(post: Post) {
        val directions = PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(post)
        findNavController().navigate(directions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postList.adapter = adapter
        binding.postList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewModel.getPosts()

        viewModel.posts.observe(viewLifecycleOwner, {
            adapter.setPosts(it)
        })
    }

    override fun onStart() {
        super.onStart()
        errorJob = lifecycleScope.launch {
            viewModel.error.collect {
                showSnackBarError(it)
            }
        }
    }

    private fun showSnackBarError(errorMessage: String) {
        Log.d(TAG, errorMessage)
        Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun onStop() {
        errorJob?.cancel()
        super.onStop()
    }
}