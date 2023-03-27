package com.example.postapipractise.IsTyping
//
//import com.example.postapipractise.Message.SendMessage.MessageApi
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.POST
//import retrofit2.http.Path
//
//interface ChatService {
//    @POST("chats/153464/typing/")
//    suspend fun sendTypingNotification(@Path("153464") chatId: String)
//}
//class Typing(username:String,password:String){
//
//    var username=username
//    var password=password
//
//
//    fun MessageInstance(): MessageApi {
//        val loggingInterceptor= HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        val url = "https://api.chatengine.io/"
//
//        val httpClient= OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("Project-ID", "52690bdb-3b85-4b96-9081-27fa9b4dc10e")
//                    .addHeader("User-Name", username)
//                    .addHeader("User-Secret", password)
//                    //.addHeader("Accept", "application/json")
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//
//        val retrofit= Retrofit.Builder()
//            .baseUrl(url)
//            .client(httpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build().create(MessageApi::class.java)
//
//        return  retrofit!!
//    }
//}