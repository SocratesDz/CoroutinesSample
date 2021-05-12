package com.socratesdiaz.coroutinesamples.base

import android.content.Context
import com.socratesdiaz.coroutinesamples.features.apifetch.datasource.*
import com.socratesdiaz.coroutinesamples.features.apifetch.repository.PostRepository
import com.socratesdiaz.coroutinesamples.features.apifetch.repository.PostRepositoryImpl
import com.socratesdiaz.coroutinesamples.features.apifetch.service.ApiService
import com.socratesdiaz.coroutinesamples.features.timer.Timer
import com.socratesdiaz.coroutinesamples.features.timer.TimerImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import org.kodein.di.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
fun diModule(appContext: Context) = DI.Module("appDependencies") {
    bind<ApiService>() with singleton {
        val httpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }

    bind<CoroutineDispatchers>() with singleton { CoroutineDispatchersImpl }
    bind<Timer>() with provider { TimerImpl(CoroutineDispatchersImpl) }
    bind<LocalPostDataSource>() with provider { LocalPostDataSourceImpl() }
    bind<RemotePostDataSource>() with provider { RemotePostDataSourceImpl(instance()) }
    bind<ConnectivityProvider>() with provider { ConnectivityProviderImpl(appContext) }
    bind<PostRepository>() with provider { PostRepositoryImpl(instance(), instance(), instance(), instance()) }
}