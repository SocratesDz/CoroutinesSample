package com.socratesdiaz.coroutinesamples.features.apifetch.datasource

import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Comment
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post

interface PostDataSource {
    suspend fun getPosts(): Result<List<Post>>
    suspend fun getPostsById(id: Int): Result<Post>
    suspend fun getCommentsByPostId(postId: Int): Result<List<Comment>>
}