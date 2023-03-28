package com.example.postapipractise.GetAllChats

import com.example.postapipractise.GetAllChats.ChatDataModel.GetChatsDataClass
import com.example.postapipractise.Login.Network.url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GetMyChats {

    @GET("/chats")
    fun getChats(): Call<List<GetChatsDataClass>?>?
}

class GetMyChatsClass(username:String,password:String){

    var username=username
    var password=password


    fun getMsgInstance(): GetMyChats {
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", "52690bdb-3b85-4b96-9081-27fa9b4dc10e")
                    .addHeader("User-name", username)
                    .addHeader("User-Secret", password)
                    //.addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create( GetMyChats::class.java)

        return  retrofit!!
    }
}