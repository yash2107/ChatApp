package com.example.postapipractise.Login.LoginDataClass

data class LoginData(
    val username:String,
    val secret: String,
    val is_authenticated:Boolean
)