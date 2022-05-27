package com.example.newsapp.data.module

import com.example.newsapp.BuildConfig.URL_API
import com.example.newsapp.data.HeadlinesRepository
import com.example.newsapp.data.HeadlinesRepositoryImpl
import com.example.newsapp.data.api.HeadlinesApi
import com.example.newsapp.data.datasources.HeadlinesDataSources
import com.example.newsapp.data.datasources.HeadlinesDataSourcesImpl
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModules = module {
    single { GsonBuilder().create() }
    single {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(URL_API)
            .addConverterFactory(
                GsonConverterFactory.create(get())
            )
            .client(get())
            .build()
            .create(HeadlinesApi::class.java)

    }
}
val dataLayer = module {
    single<HeadlinesDataSources> { HeadlinesDataSourcesImpl(headlinesApi = get()) }
    single<HeadlinesRepository> { HeadlinesRepositoryImpl(dataSources = get()) }
}