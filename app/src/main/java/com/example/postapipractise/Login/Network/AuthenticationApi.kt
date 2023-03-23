package com.example.postapipractise.Login.Network

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

/*interface AuthenticationApi{
    @GET("me/")
    fun getUsers(): Call<LoginData?>?

    companion object{
        var authenticationApi: AuthenticationApi?=null
        fun getInstance(username: String,secret: String): AuthenticationApi {
            if(authenticationApi ==null){
                val url = "https://api.chatengine.io/users/"
                val projectKey = "52690bdb-3b85-4b96-9081-27fa9b4dc10e"
                val okHttpClient = getOkhttpClient(projectKey,username,secret)
                authenticationApi = Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(AuthenticationApi::class.java)
            }

            return authenticationApi!!
        }

        private fun getOkhttpClient(projectKey:String,username: String,secret: String): OkHttpClient {

            // Define your header key for the private key
            val headerKey = "PROJECT-KEY"

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader(headerKey, projectKey)
                        .addHeader("User-Name",username)
                        .addHeader("User-Secret",secret)
                        //.addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                }
                .build()
        }
    }
}

*/

const val url = "https://api.chatengine.io/users/"
interface AuthenticationApi {

    @GET("me/")
    fun getUsers(): Call<LoginData?>?
}

class LoginClass(username:String,password:String){

    var username=username
    var password=password


    fun getInstance(): AuthenticationApi{
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
            .build().create(AuthenticationApi::class.java)

        return retrofit!!
    }
}
