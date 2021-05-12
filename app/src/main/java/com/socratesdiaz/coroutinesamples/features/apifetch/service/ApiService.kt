package com.socratesdiaz.coroutinesamples.features.apifetch.service

import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Comment
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Post>

    @GET("/posts/{id}/comments")
    suspend fun getCommentByPostId(@Path("id") postId: Int): Response<List<Comment>>
}