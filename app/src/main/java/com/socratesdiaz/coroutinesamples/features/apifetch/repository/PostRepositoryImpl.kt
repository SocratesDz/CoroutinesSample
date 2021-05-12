package com.socratesdiaz.coroutinesamples.features.apifetch.repository

import com.socratesdiaz.coroutinesamples.base.CoroutineDispatchers
import com.socratesdiaz.coroutinesamples.features.apifetch.datasource.ConnectivityProvider
import com.socratesdiaz.coroutinesamples.features.apifetch.datasource.LocalPostDataSource
import com.socratesdiaz.coroutinesamples.features.apifetch.datasource.RemotePostDataSource
import com.socratesdiaz.coroutinesamples.features.apifetch.datasource.Result
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Comment
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepositoryImpl(
    private val localDataSource: LocalPostDataSource,
    private val remoteDataSource: RemotePostDataSource,
    private val connectivityProvider: ConnectivityProvider,
    private val coroutineDispatchers: CoroutineDispatchers
) : PostRepository {
    override suspend fun getPosts(): Flow<Result<List<Post>>> {
        return flow {
            if (connectivityProvider.isConnected()) {
                emit(remoteDataSource.getPosts())
            } else {
                emit(localDataSource.getPosts())
            }
        }.flowOn(coroutineDispatchers.io)
    }

    override suspend fun getPostById(id: Int): Flow<Result<Post>> {
        return flow {
            if(connectivityProvider.isConnected()) {
                emit(remoteDataSource.getPostsById(id))
            } else {
                emit(localDataSource.getPostsById(id))
            }
        }.flowOn(coroutineDispatchers.io)
    }

    override suspend fun getCommentsByPost(id: Int): Flow<Result<List<Comment>>> {
        return flow {
            if(connectivityProvider.isConnected()) {
                emit(remoteDataSource.getCommentsByPostId(id))
            } else {
                emit(localDataSource.getCommentsByPostId(id))
            }
        }
    }
}