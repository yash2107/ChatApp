package com.example.postapipractise.ChatRoom.DataModel

data class ChatRoomDataModel(
    //DataClass for the Chatting Screen
    val title: String,
    val is_direct_chat: Boolean,
    var usernames: List<String>
)