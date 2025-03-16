package com.team8.tsuinskips.data

import com.team8.tsuinskips.data.datasource.ApiRequests
import com.team8.tsuinskips.data.datasource.ApiUser
import com.team8.tsuinskips.domain.Token
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private const val BASE_URL = "http://90.188.93.70:39965/"
    private var token = " "
    private val client = OkHttpClient()
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val clientBuilder: OkHttpClient.Builder =
        client.newBuilder().addInterceptor(interceptor).addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder().header(
                "Authorization", "Bearer $token"
            )

            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
            .client(
                clientBuilder.build()
            ).build()
    }
    val Auth: ApiUser by lazy {
        retrofit.create(ApiUser::class.java)
    }

    val Requests: ApiRequests by lazy {
        retrofit.create(ApiRequests::class.java)
    }

    fun updateToken(tokenJ: Token) {
        token = tokenJ.key
    }

    fun getToken(): String {
        return token
    }
}