package com.example.postapipractise.Login.ViewModel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomApi
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomClass
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.Login.LoginDataClass.LoginData
import com.example.postapipractise.Login.Network.AuthenticationApi
import com.example.postapipractise.Login.Network.LoginClass
import com.example.postapipractise.Message.SendMessage.MessageApi
import com.example.postapipractise.Message.SendMessage.MessageClass
import com.example.postapipractise.Message.MessageDataClass
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass

@SuppressLint("MutableCollectionMutableState")
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

    val initial3 = MessageDataClass("")
    var sendChat: MessageDataClass? by mutableStateOf(initial3)
    var SendList:MutableList<MessageDataClass> by mutableStateOf(mutableListOf())

//    var text by mutableStateOf("")
    fun sendMessage(): MessageApi {
        val messageApi = MessageClass(user_name,password).MessageInstance()
        return messageApi
    }

    private var initial4 = ReceiveDataClass("","","")
    var receiveChat : ReceiveDataClass? by mutableStateOf(initial4)
    var chatList:MutableList<ReceiveDataClass>by mutableStateOf(mutableListOf() )
    fun receiveMessage(): MessageApi {
        val receiveApi = MessageClass(user_name,password).MessageInstance()
        return receiveApi
    }

//    val messages = mutableListOf<MessageDataClass>()

//    fun updateUIWithNewMessage(message: ReceiveDataClass) {
//        chatList.add(message)
//        Log.d("LOGINVIEW", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%$chatList")
//    }


//private var initial4 = ReceiveDataClass("", "", "")
//    var receiveChat: ReceiveDataClass? by mutableStateOf(initial4)
//    var chatList: MutableList<ReceiveDataClass> by mutableStateOf(mutableStateListOf())
//
//    fun receiveMessage() {
//        val receiveApi = MessageClass(user_name,password).MessageInstance()
//        receiveApi.getMessage()?.enqueue(object : Callback<MutableList<ReceiveDataClass>?> {
//            override fun onResponse(
//                call: Call<MutableList<ReceiveDataClass>?>,
//                response: Response<MutableList<ReceiveDataClass>?>
//            ) {
//                val messages = response.body()
//                if (messages != null) {
//                    for (message in messages) {
//                        updateUIWithNewMessage(message)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<MutableList<ReceiveDataClass>?>, t: Throwable) {
//                Log.e("LOGINVIEW", "Failed to receive messages: ${t.message}")
//            }
//        })
//    }

    fun updateUIWithNewMessage(message: ReceiveDataClass) {
        chatList.add(message)
    }
}





