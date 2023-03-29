package com.example.postapipractise.GetAllChats.ChatDataModel

data class GetChatsDataClass(
    val created: String,
    val last_message: LastMessage,
    val people: List<People>,
    val title:String,
    val id:Int,
    val access_key: String
)
