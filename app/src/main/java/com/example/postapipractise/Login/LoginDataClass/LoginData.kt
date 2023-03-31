package com.example.postapipractise.Login.LoginDataClass

data class LoginData(
    //Data Class for login Authentication
    val username:String,
    val secret: String,
    val is_authenticated:Boolean
)