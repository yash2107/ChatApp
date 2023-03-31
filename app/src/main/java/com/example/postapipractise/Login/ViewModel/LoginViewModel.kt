package com.example.postapipractise.Login.ViewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomApi
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomClass
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.GetAllChats.ChatDataModel.GetChatsDataClass
import com.example.postapipractise.GetAllChats.GetMyChats
import com.example.postapipractise.GetAllChats.GetMyChatsClass
import com.example.postapipractise.Login.LoginDataClass.LoginData
import com.example.postapipractise.Login.Network.AuthenticationApi
import com.example.postapipractise.Login.Network.LoginClass
import com.example.postapipractise.Message.MessageModel.MessageDataClass
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import com.example.postapipractise.Message.SendMessage.MessageApi
import com.example.postapipractise.Message.SendMessage.MessageClass
import com.example.postapipractise.TypingStatus.TypingApi
import com.example.postapipractise.TypingStatus.TypingClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("MutableCollectionMutableState")
class LoginViewModel: ViewModel() {
    var user_name = mutableStateOf("")
    var password = mutableStateOf("")

    var title by mutableStateOf("")

    val initial = LoginData("", "", false)
    var UserData: LoginData? by mutableStateOf(initial)

    val initail2 = ChatRoomDataModel("",false, listOf("Admin"))
    var chatData: ChatRoomDataModel? by mutableStateOf(initail2)

    var isLoading = mutableStateOf(false)

    var chatId by mutableStateOf(0)
    var accesskey by mutableStateOf("")

    fun AuthenticateUser(): AuthenticationApi {
        val apiService = LoginClass(user_name.value,password.value).getInstance()

        return apiService
    }
    fun createRoom():ChatRoomApi{
        val chatRoomApi = ChatRoomClass(user_name.value,password.value).postInstance()
        return chatRoomApi
    }

    val initial3 = MessageDataClass("")
    var sendChat: MessageDataClass? by mutableStateOf(initial3)

    fun sendMessage(): MessageApi {
        val messageApi = MessageClass(user_name.value,password.value,chatId).MessageInstance()
        return messageApi
    }

    private var initial4 = ReceiveDataClass("","","")
    var receiveChat : ReceiveDataClass? by mutableStateOf(initial4)
    var chatList:MutableList<ReceiveDataClass>by mutableStateOf(mutableListOf() )
    fun receiveMessage(): MessageApi {
        val receiveApi = MessageClass(user_name.value,password.value,chatId).MessageInstance()
        Log.i("MYTAG", "################## ${chatId}")
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
        val msgApiService= GetMyChatsClass(user_name.value,password.value).getMsgInstance()
        return  msgApiService
    }

    val istyping = mutableStateOf(false)
    val istypinguser= mutableStateOf("")

    @SuppressLint("SuspiciousIndentation")
    fun IsUserTyping(): TypingApi {
        val apiService= TypingClass(user_name.value,password.value,chatId.toString()).getTypingInstance()
        return apiService
    }
    fun starttyping(){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                delay(2000L)
            }
            istyping.value=false
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
//            .padding(top = 500.dp)
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}









