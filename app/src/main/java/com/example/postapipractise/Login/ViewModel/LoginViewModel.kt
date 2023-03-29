package com.example.postapipractise.Login.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("MutableCollectionMutableState")
class LoginViewModel: ViewModel() {
    var user_name = mutableStateOf("")
    var password = mutableStateOf("")

    val initial = LoginData("", "", false)
    var UserData: LoginData? by mutableStateOf(initial)

    val initail2 = ChatRoomDataModel("",false)
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

    // Other properties and functions

        private val _isTyping = MutableLiveData<Boolean>(false)
        val isTyping: LiveData<Boolean>
            get() = _isTyping

    fun updateTypingStatus(isTyping: Boolean) {
        _isTyping.value = isTyping
    }

}


@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val dot1Size = remember { mutableStateOf(12.dp) }
        val dot2Size = remember { mutableStateOf(12.dp) }
        val dot3Size = remember { mutableStateOf(12.dp) }

        LaunchedEffect(Unit) {
            while (true) {
                dot1Size.value = 20.dp
                dot2Size.value = 12.dp
                dot3Size.value = 12.dp
                delay(250)
                dot1Size.value = 12.dp
                dot2Size.value = 20.dp
                dot3Size.value = 12.dp
                delay(250)
                dot1Size.value = 12.dp
                dot2Size.value = 12.dp
                dot3Size.value = 20.dp
                delay(250)
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
//                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(dot1Size.value)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(MaterialTheme.colors.primary, CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(dot2Size.value)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(MaterialTheme.colors.secondary, CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(dot3Size.value)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(MaterialTheme.colors.primaryVariant, CircleShape)
                )
            }
        }
    }
}














