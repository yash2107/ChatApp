package com.example.postapipractise.Signup.Model.Network

import com.example.postapipractise.Signup.Model.DataModel.DataModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitAPI {
    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users

    @POST("users/")
    fun postData(@Body dataModel: DataModel?): Call<DataModel?>?

    companion object{
        var retrofitAPI: RetrofitAPI?=null
        fun postInstance(): RetrofitAPI {
            if(retrofitAPI ==null){
                val url = "https://api.chatengine.io/"
                val privateKey = "64faa440-939e-4eba-948a-7146a70e7a5b"
                val okHttpClient = getOkhttpClient(privateKey)
                retrofitAPI = Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitAPI::class.java)
            }

            return retrofitAPI!!
        }

        private fun getOkhttpClient(privateKey:String):OkHttpClient{

            // Define your header key for the private key
            val headerKey = "PRIVATE-KEY"

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader(headerKey, privateKey)
                        //.addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                }
                .build()
        }
    }
}
