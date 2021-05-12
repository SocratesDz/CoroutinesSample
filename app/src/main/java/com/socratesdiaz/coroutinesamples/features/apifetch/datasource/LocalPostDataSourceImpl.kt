package com.socratesdiaz.coroutinesamples.features.apifetch.datasource

import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Comment
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post

class LocalPostDataSourceImpl : LocalPostDataSource {
    override suspend fun getPosts(): Result<List<Post>> = Result.success(emptyList())

    override suspend fun getPostsById(id: Int): Result<Post> = Result.success(Post(0, 0, "", ""))

    override suspend fun getCommentsByPostId(postId: Int): Result<List<Comment>> =
        Result.success(emptyList())
}