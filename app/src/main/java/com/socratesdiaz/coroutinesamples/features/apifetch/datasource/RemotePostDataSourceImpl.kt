package com.socratesdiaz.coroutinesamples.features.apifetch.datasource

import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Comment
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post
import com.socratesdiaz.coroutinesamples.features.apifetch.service.ApiService
import retrofit2.Response
import java.lang.Exception

class RemotePostDataSourceImpl(private val apiService: ApiService) : RemotePostDataSource {
    override suspend fun getPosts(): Result<List<Post>> {
        return getResponseResult { apiService.getPosts() }
    }

    override suspend fun getPostsById(id: Int): Result<Post> {
        return getResponseResult { apiService.getPostById(id) }
    }

    override suspend fun getCommentsByPostId(postId: Int): Result<List<Comment>> {
        return getResponseResult { apiService.getCommentByPostId(postId) }
    }

    private suspend fun <T> getResponseResult(request: suspend () -> Response<T>): Result<T> {
        try {
            val response = request.invoke()
            if (response.isSuccessful) {
                return Result.success(response.body())
            }
            val errorMessage = response.errorBody()?.string()
            return Result.error(error = Error(errorMessage), message = errorMessage ?: "")
        } catch (ex: Exception) {
            return Result.error(error = Error(ex), message = ex.message ?: "")
        }
    }
}