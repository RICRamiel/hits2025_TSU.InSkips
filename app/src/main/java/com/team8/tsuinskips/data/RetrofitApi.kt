package com.team8.tsuinskips.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.team8.tsuinskips.BuildConfig
import com.team8.tsuinskips.common.application.InSkipsApplication
import com.team8.tsuinskips.data.datasource.ApiRequests
import com.team8.tsuinskips.data.datasource.ApiUser
import com.team8.tsuinskips.data.datasource.UserLoginDTO
import com.team8.tsuinskips.domain.Token
import com.team8.tsuinskips.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine
import com.team8.tsuinskips.data.mapper.TokenMapper

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
            val resp = chain.proceed(request)

            if (resp.code == 401) {
                val email = InSkipsApplication.getApp().appSharedPref.getString("email", "")
                val passwd = InSkipsApplication.getApp().appSharedPref.getString("passwd", "")
                Log.i("EMAIL_PASS", email + passwd)
                if (email!!.isNotEmpty() && passwd!!.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        token = ""
                        val work = async { return@async Auth.login(UserLoginDTO(email, passwd)) }
                        val res = work.await()
                        token = res.body()!!.token
                    }
                }
            }
            return@Interceptor resp
        })
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(getContentType())).client(
                clientBuilder.build()
            ).build()
    }

    private fun getContentType() = "application/json".toMediaType()

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