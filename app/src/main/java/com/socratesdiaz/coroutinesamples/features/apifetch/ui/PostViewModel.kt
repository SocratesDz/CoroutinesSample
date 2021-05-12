package com.socratesdiaz.coroutinesamples.features.apifetch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socratesdiaz.coroutinesamples.base.CoroutineDispatchers
import com.socratesdiaz.coroutinesamples.features.apifetch.datasource.Result
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post
import com.socratesdiaz.coroutinesamples.features.apifetch.repository.PostRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class PostViewModel(
    private val postRepository: PostRepository,
    private val coroutineDispatchers: CoroutineDispatchers
) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _timeElapsed = MutableLiveData<String>()
    val timeElapsed: LiveData<String> = _timeElapsed

    fun getPosts() {
        viewModelScope.launch(coroutineDispatchers.io) {
            val duration = measureTimeMillis {
                postRepository.getPosts().collect {
                    when (it.status) {
                        Result.Status.SUCCESS -> {
                            withContext(coroutineDispatchers.main) {
                                _posts.value = it.data
                            }
                        }
                        Result.Status.ERROR -> {
                            _error.offer(it.message ?: "")
                        }
                    }
                }
            }
            withContext(coroutineDispatchers.main) {
                _timeElapsed.value = "Posts fetched in: ${duration/1000.0} s"
            }
        }
    }

    override fun onCleared() {
        _error.close()
        super.onCleared()
    }
}