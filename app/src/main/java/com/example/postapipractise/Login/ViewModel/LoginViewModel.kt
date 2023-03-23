package com.example.postapipractise.Login.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postapipractise.Login.LoginDataClass.LoginData
import com.example.postapipractise.Login.Network.AuthenticationApi
import com.example.postapipractise.Login.Network.LoginClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

//class LoginViewModel : ViewModel() {
//
//    private val authenticationApi = AuthenticationApi.getInstance()
//
//    suspend fun authenticateUser(username: String, secret: String): Result<LoginData> {
//        return try {
//            val response = authenticationApi.authenticateUser(username, secret)
//            if (response.isSuccessful) {
//                Result.success(response.body()!!)
//            } else {
//                Result.failure(Exception("Failed to authenticate user. Error code: ${response.code()}"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}



class LoginViewModel: ViewModel() {
    var user_name by mutableStateOf("")
    var password by mutableStateOf("")

    val initial = LoginData("", "", false)
    var UserData: LoginData? by mutableStateOf(initial)

    fun AuthenticateUser(): AuthenticationApi {
        val apiService = LoginClass(user_name,password).getInstance()

        return apiService
    }
}





