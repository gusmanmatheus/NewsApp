package com.example.newsapp.data.api

import android.util.Log
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class Response <T> private constructor(
    private val value: T?,
    private val failure: Throwable?
) {
    val isSuccess: Boolean
        get() = value != null

    private val isFailure: Boolean
        get() = failure != null

    fun getOrThrow(): T = value ?: error("Null Value")
    fun getError(): Throwable = failure ?: Throwable("Erro nao possivel de ser pego")

    companion object {
        fun <T> success(value: T?): Response<T> {
            return Response(value, null)
        }

        fun <T> error(error: Throwable?): Response<T> {
            return Response(null, error)
        }
    }

    inline fun onSuccess(action: (value: T) -> Unit): Response<T> = this.apply {
        if (isSuccess) action(getOrThrow())
    }

    fun onFailure(action: (exception: Throwable) -> Unit): Response<T> = this.apply {
        if (isFailure) action(failure!!)
    }
}

suspend fun <T> requestWrapper(request: suspend () -> T): Response<T> {
    return try {
        Response.success(request())
    } catch (httpException: HttpException) {
        Response.error(Throwable(httpException))
    } catch (ioException: IOException) {
        Response.error(Throwable("error Na internet"))
    } catch (e: Exception) {
        e
        Response.error(Throwable("error desconhecido"))
    }
}