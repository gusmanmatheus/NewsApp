package com.example.newsapp.data.module

import android.util.Log
import com.example.newsapp.BuildConfig
import com.example.newsapp.BuildConfig.URL_API
import com.example.newsapp.data.HeadlinesRepository
import com.example.newsapp.data.HeadlinesRepositoryImpl
import com.example.newsapp.data.api.HeadlinesApi
import com.example.newsapp.data.datasources.HeadlinesDataSources
import com.example.newsapp.data.datasources.HeadlinesDataSourcesImpl
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val AUTH_INTERCEPTOR = "AUTH_INTERCEPTOR"
const val API_KEY_NAME = "apiKey"

val networkModules = module {
    single { GsonBuilder().create() }

    single(AUTH_INTERCEPTOR.toQualifier()) {
        Interceptor { chain ->
            val originalUrl = chain.request().url()
            val newUrl = originalUrl.newBuilder().addQueryParameter(
                API_KEY_NAME, BuildConfig.API_KEY
            ).toString()
            chain.proceed(chain.request().newBuilder().url(newUrl).build())
        }
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(get(AUTH_INTERCEPTOR.toQualifier()))
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

fun String.toQualifier() = named(this)
