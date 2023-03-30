package com.example.postapipractise.ChatRoom.DataModel


import com.example.postapipractise.Login.Network.AuthenticationApi
import com.example.postapipractise.Login.Network.url
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ChatRoomApi {
    @PUT("chats/")
    fun postChatRoom(@Body chatRoomDataModel: ChatRoomDataModel?): Call<ChatRoomDataModel?>?
}

class ChatRoomClass(username:String,password:String){

    var username=username
    var password=password


    fun postInstance(): ChatRoomApi {
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val url = "https://api.chatengine.io/"

        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", "52690bdb-3b85-4b96-9081-27fa9b4dc10e")
                    .addHeader("User-Name", username)
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
            .build().create(ChatRoomApi::class.java)

        return  retrofit!!
    }
}