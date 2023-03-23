package com.example.postapipractise.ChatRoom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Message.MessageDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnrememberedMutableState")
@Composable
fun ChatRoomScreen(navController: NavController,loginViewModel: LoginViewModel) {
    val txt = remember{mutableStateOf("")}
//    val txt = loginViewModel.text
    val title = loginViewModel.user_name
    val ctx = LocalContext.current
    val result = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
//        Text(text = "Start Chatting")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(value = txt.value, onValueChange = {
                txt.value =it
            }, modifier = Modifier.weight(1f))
            IconButton(onClick = {
                postSenderMessage(ctx,title,txt.value,result,loginViewModel)
            }) {
                Icon(Icons.Filled.Send, contentDescription = "")
            }
        }
    }
    Text(text = result.value)
}

private fun postSenderMessage(
    ctx: Context,
    title:String,
    txt:String,
    result: MutableState<String>,
    loginViewModel: LoginViewModel
){
    val messageApi = loginViewModel.sendMessage()
    val messageDataClass = MessageDataClass(txt)
    val call: Call<MessageDataClass?>? =messageApi.postMessage(messageDataClass)

    call!!.enqueue(object: Callback<MessageDataClass?>{
        override fun onResponse(
            call: Call<MessageDataClass?>,
            response: Response<MessageDataClass?>
        ) {
            Toast.makeText(ctx,"Message Send", Toast.LENGTH_SHORT).show()
            val model: MessageDataClass?=response.body()
            val resp = model?.text
            if (resp != null) {
                result.value = resp
            }
            loginViewModel.sendChat =model
        }

        override fun onFailure(call: Call<MessageDataClass?>, t: Throwable) {
            result.value = "Error "+ t.message
        }

    }
    )
}




