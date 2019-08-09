package nl.tmg.core.di

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url().newBuilder()
            .addQueryParameter("apiKey", apiKey)
            .build()
        val requestBuilder = original.newBuilder().url(originalHttpUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}