package com.example.postapipractise.Message.SendMessage


import com.example.postapipractise.Message.MessageDataClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageApi {

//    @GET("message/")
//    fun getMessage():Call<ReceiveDataClass?>?

    @POST("messages/")
    fun postMessage(@Body messageDataClass: MessageDataClass?): Call<MessageDataClass?>?
}

class MessageClass(username:String,password:String){

    var username=username
    var password=password


    fun MessageInstance(): MessageApi {
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val url = "https://api.chatengine.io/chats/153464/"

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
            .build().create(MessageApi::class.java)

        return  retrofit!!
    }
}
