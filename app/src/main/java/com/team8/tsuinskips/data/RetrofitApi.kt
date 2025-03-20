package com.team8.tsuinskips.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.team8.tsuinskips.BuildConfig
import com.team8.tsuinskips.data.datasource.ApiRequests
import com.team8.tsuinskips.data.datasource.ApiUser
import com.team8.tsuinskips.domain.Token
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitApi {
    private val BASE_URL
        get() = BuildConfig.RETROFIT_BASE_URL
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
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(getContentType()))
            .client(
                clientBuilder.build()
            ).build()
    }
    private fun getContentType() =
        "application/json".toMediaType()

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