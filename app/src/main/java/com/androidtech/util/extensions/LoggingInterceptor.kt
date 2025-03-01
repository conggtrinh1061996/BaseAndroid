package com.androidtech.util.extensions

import com.androidtech.base.BuildConfig
import com.androidtech.util.Logger
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import kotlin.jvm.Throws

class LoggingInterceptor: Interceptor {

    private val defaultErrorCode = 999

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        Logger.d("Request url: ${originalRequest.url}")

        // Create new request with header
        val modifierRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TOKEN}")
            .build()

        try {
            val response = chain.proceed(modifierRequest)
            Logger.d("Response body: ${chain.proceed(modifierRequest.newBuilder().build()).body}")
            return response
        } catch (e: IOException) {
            e.printStackTrace()

            val errorMessage = when (e) {
                else -> e.message ?: "Error occurred"
            }

            return Response.Builder()
                .request(modifierRequest)
                .code(defaultErrorCode)
                .message(errorMessage)
                .body("$e".toResponseBody(null))
                .build()
        }
    }
}