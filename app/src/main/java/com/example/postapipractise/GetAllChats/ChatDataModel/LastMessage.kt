package com.example.postapipractise.GetAllChats.ChatDataModel

data class LastMessage(
    val attachments: List<Any>,
    val created: String,
    val custom_json: Any,
    val sender_username: String,
    val text: String

)
