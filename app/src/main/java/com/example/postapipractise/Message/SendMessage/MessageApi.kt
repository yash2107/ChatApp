package com.example.postapipractise.Message.SendMessage


import com.example.postapipractise.Common.Constants
import com.example.postapipractise.Message.MessageModel.MessageDataClass
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageApi {

    @GET("messages/")
    fun getMessage():Call<List<ReceiveDataClass>?>?

    @POST("messages/")
    fun postMessage(@Body messageDataClass: MessageDataClass?): Call<MessageDataClass?>?
}

class MessageClass(var username:String,var password:String,val chatId:Int){

    fun MessageInstance(): MessageApi {
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val url = "https://api.chatengine.io/chats/${chatId}/"

        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", Constants.PROJECT_ID)
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
            .build().create(MessageApi::class.java)

        return  retrofit!!
    }
}
