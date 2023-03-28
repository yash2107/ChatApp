package com.example.postapipractise.TypingStatus

import com.example.postapipractise.Message.SendMessage.MessageApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface TypingApi {

    @POST("typing/")
    fun notifyTyping(): Call<Void>
}


class TypingClass(username:String,password:String) {
    private val username=username
    private val password=password

    fun getTypingInstance(): TypingApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val url = "https://api.chatengine.io/chats/4544444/"

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", "52690bdb-3b85-4b96-9081-27fa9b4dc10e")
                    .addHeader("User-Name", username)
                    .addHeader("User-Secret", password)
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TypingApi::class.java)
    }
}

