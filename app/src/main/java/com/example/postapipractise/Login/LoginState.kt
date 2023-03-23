package com.example.postapipractise.Login

import com.example.postapipractise.Login.LoginDataClass.LoginData

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val data: LoginData) : LoginState()
    data class Error(val message: String?) : LoginState()
}

