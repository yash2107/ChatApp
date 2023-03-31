package com.example.postapipractise.GetAllChats

import com.example.postapipractise.Common.Constants
import com.example.postapipractise.GetAllChats.ChatDataModel.GetChatsDataClass
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

class GetMyChatsClass(val username:String,val password:String){

    fun getMsgInstance(): GetMyChats {
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", Constants.PROJECT_ID)
                    .addHeader("User-name", username)
                    .addHeader("User-Secret", password)
                    //.addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl(Constants.url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create( GetMyChats::class.java)

        return  retrofit!!
    }
}