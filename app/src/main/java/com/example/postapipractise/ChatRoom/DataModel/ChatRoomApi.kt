package com.example.postapipractise.ChatRoom.DataModel


import com.example.postapipractise.Common.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT

interface ChatRoomApi {
    // Interface defining the REST API endpoints for the Chat Room feature with a PUT request to post a new Chat Room.
    @PUT("chats/")
    fun postChatRoom(@Body chatRoomDataModel: ChatRoomDataModel?): Call<ChatRoomDataModel?>?
}

class ChatRoomClass(var username:String,var password:String){
    // Class representing a Chat Room instance that takes in a username and password for authentication.
    fun postInstance(): ChatRoomApi {
        // The postInstance() function returns a ChatRoomApi instance created using OkHttpClient, Retrofit, and Gson.
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val url = "https://api.chatengine.io/"

        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", Constants.PROJECT_ID)
                    .addHeader("User-Name", username)
                    .addHeader("User-Secret", password)
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
        // Retrofit implementation of ChatRoomApi will be used for API calls to ChatEngine server to create and manage Chat Rooms.
    }
}