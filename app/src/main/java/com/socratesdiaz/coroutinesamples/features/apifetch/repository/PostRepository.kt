package com.socratesdiaz.coroutinesamples.features.apifetch.repository

import com.socratesdiaz.coroutinesamples.features.apifetch.datasource.Result
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Comment
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(): Flow<Result<List<Post>>>
    suspend fun getPostById(id: Int): Flow<Result<Post>>
    suspend fun getCommentsByPost(id: Int): Flow<Result<List<Comment>>>
}