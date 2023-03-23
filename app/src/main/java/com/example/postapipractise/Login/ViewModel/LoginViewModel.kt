package com.example.postapipractise.Login.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomApi
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomClass
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.Login.LoginDataClass.LoginData
import com.example.postapipractise.Login.Network.AuthenticationApi
import com.example.postapipractise.Login.Network.LoginClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    var user_name by mutableStateOf("")
    var password by mutableStateOf("")

    val initial = LoginData("", "", false)
    var UserData: LoginData? by mutableStateOf(initial)

    val initail2 = ChatRoomDataModel("",false)
    var chatData: ChatRoomDataModel? by mutableStateOf(initail2)
    fun AuthenticateUser(): AuthenticationApi {
        val apiService = LoginClass(user_name,password).getInstance()

        return apiService
    }
    fun createRoom():ChatRoomApi{
        val chatRoomApi = ChatRoomClass(user_name,password).postInstance()
        return chatRoomApi
    }
}





