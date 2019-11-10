package com.guilhermembisotto.data

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.url.newBuilder().build()
        return chain.proceed(request.newBuilder().url(authenticatedRequest).build())
    }
}