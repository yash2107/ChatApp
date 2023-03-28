package com.example.postapipractise.Login.ViewModel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomApi
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomClass
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.GetAllChats.ChatDataModel.GetChatsDataClass
import com.example.postapipractise.GetAllChats.GetMyChats
import com.example.postapipractise.GetAllChats.GetMyChatsClass
import com.example.postapipractise.Login.LoginDataClass.LoginData
import com.example.postapipractise.Login.Network.AuthenticationApi
import com.example.postapipractise.Login.Network.LoginClass
import com.example.postapipractise.Message.MessageDataClass
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import com.example.postapipractise.Message.SendMessage.MessageApi
import com.example.postapipractise.Message.SendMessage.MessageClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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

    fun updateUIWithNewMessage(message: ReceiveDataClass) {
        chatList.add(message)
    }

    private val _messageList = MutableStateFlow(emptyList<ReceiveDataClass>())
    val messageList: StateFlow<List<ReceiveDataClass>> = _messageList

    fun updateMessageList(newList: List<ReceiveDataClass>) {
        _messageList.value =newList
    }

    var allChats : MutableList<GetChatsDataClass> by mutableStateOf(mutableListOf())
    //var newMsgDetailsGet:MsgDataClassModel? by mutableStateOf(firstMsgGet)
    fun getAllChats(): GetMyChats {
        val msgApiService= GetMyChatsClass(user_name,password).getMsgInstance()
        return  msgApiService
    }


//    private val typingApi = TypingClass(user_name, password).getTypingInstance()
//
//    fun updateTypingStatus(isTyping: Boolean) {
//        val request = typingApi.notifyTyping()
//        if (isTyping) {
//            // Notify the server that the user is currently typing
//            request.enqueue(object : Callback<Void> {
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    // Do nothing
//                }
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    Log.e("Typing", "Failed to send typing notification", t)
//                }
//            })
//        } else {
//            // Notify the server that the user is no longer typing
//            request.cancel()
//        }
//    }
}





