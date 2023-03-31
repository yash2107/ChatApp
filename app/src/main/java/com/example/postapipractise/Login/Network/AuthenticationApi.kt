package com.example.postapipractise.Login.Network

import com.example.postapipractise.Common.Constants
import com.example.postapipractise.Login.LoginDataClass.LoginData
import com.example.postapipractise.Signup.Model.Network.RetrofitAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface AuthenticationApi {

    @GET("users/me/")
    fun getUsers(): Call<LoginData?>?
}

class LoginClass(var username:String,var password:String){
    // as we are making a get request to Authenticate the data
    // so we are annotating it with Get
    // and along with that we are passing a parameter as users

    fun getInstance(): AuthenticationApi{
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
            .build().create(AuthenticationApi::class.java)

        return retrofit!!
    }
}
