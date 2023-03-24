package com.example.postapipractise.ChatRoom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Message.MessageDataClass
import com.example.postapipractise.ui.theme.Purple500
import com.example.postapipractise.ui.theme.senderColor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter",
    "RememberReturnType"
)
@Composable
fun ChatRoomScreen(navController: NavController,loginViewModel: LoginViewModel) {
    val txt = remember { mutableStateOf("") }
    val title = loginViewModel.user_name
    val ctx = LocalContext.current
    val result = remember {
        mutableStateOf("")
    }
    val created = remember {
        mutableStateOf("")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Chat Room")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }, backgroundColor = Purple500
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.9f)
                .background(brush = Brush.verticalGradient(listOf(Color.White,Purple500), startY = 1000f, endY = 4500f))
        ) {
            itemsIndexed(loginViewModel.chatList) { index, item ->
                if (item.sender_username == loginViewModel.user_name) {
                    Column(modifier= Modifier.fillMaxWidth(),horizontalAlignment = Alignment.End,) {
                        Box(modifier = Modifier.padding(start = 40.dp,top = 8.dp, end = 8.dp)) {
                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(15.dp, 0.dp, 15.dp, 15.dp)),
                                backgroundColor = senderColor,

                            ) {
                                Column(
//                                        Modifier.fillMaxWidth(),
//                                        horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = item.text,
                                        modifier = Modifier
                                            .padding(8.dp)
                                        //.align(Alignment.Start)
                                    )
                                    Text(
                                        text = item.created.substring(12, 16),
                                        modifier = Modifier
                                            .align(Alignment.End)
                                            .padding(start = 8.dp,end = 8.dp, bottom = 4.dp)
                                    )
                                }
                            }
                        }
                    }


                } else {
                    Column(modifier= Modifier.fillMaxWidth(),horizontalAlignment = Alignment.Start) {
                        Box(modifier = Modifier.padding(start = 10.dp,top = 8.dp, end = 8.dp)) {
                            Card(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(0.dp, 15.dp, 15.dp, 15.dp)),
                                backgroundColor = Color.LightGray
                            ) {
                                Column {
                                    Text(
                                        text = item.text, modifier = Modifier
                                            .padding(8.dp)
                                    )
                                    Text(
                                        text = item.created.substring(12, 16), modifier = Modifier
                                            .align(Alignment.End)
                                            .padding(start = 8.dp, bottom = 4.dp, end = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        var textFieldValue by remember { mutableStateOf("") }
        val isTextFieldEmpty = textFieldValue.isEmpty()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        textFieldValue = newValue
                    },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(text = "Type Your Message Here") }
                )
                IconButton(
                    onClick = {
                        postSenderMessage(
                            ctx,
                            title,
                            textFieldValue,
                            result,
                            loginViewModel
                        )
                        textFieldValue = ""
                    },
                    enabled = !isTextFieldEmpty
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "",
                        tint = if (isTextFieldEmpty) Color.Gray else Color.Blue // Change the icon color based on whether the text field is empty or not
                    )
                }
            }
        }
        Text(text = result.value)
    }
}


private fun postSenderMessage(
    ctx: Context,
    title:String,
    txt:String,
//    created: MutableState<String>,
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

//@Composable
//fun ChatWebSocket(loginViewModel:LoginViewModel) {
//    val url = "wss://api.chatengine.io/person/?publicKey=52690bdb-3b85-4b96-9081-27fa9b4dc10e&username=${loginViewModel.user_name}&secret=${loginViewModel.password}"
//
//    var socket by remember { mutableStateOf<WebSocket?>(null) }
//
//    DisposableEffect(url) {
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url(url)
//            .build()
//
//        socket = client.newWebSocket(request, object : WebSocketListener() {
//            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
//                // WebSocket connection established
//                Log.d("MYTAG","Connection Open")
//            }
//
//            override fun onMessage(webSocket: WebSocket, text: String) {
//                // Handle text message received from the server
//            }
//
//            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//                // Handle binary message received from the server
//            }
//
//            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//                // WebSocket connection is about to close
////                webSocket.close(NORMAL_CLOSURE_STATUS, null)
//            }
//
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
//                // WebSocket connection failed
//            }
//        })
//
//        onDispose {
//            // Disconnect the WebSocket when the composable is removed from the composition
////            socket?.close(NORMAL_CLOSURE_STATUS, null)
//        }
//    }
//}






//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(bottom = 16.dp),
//        contentAlignment = Alignment.BottomCenter
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            OutlinedTextField(value = txt.value, onValueChange = {
//                txt.value =it
//            }, modifier = Modifier.weight(1f))
//            IconButton(onClick = {
//                postSenderMessage(ctx,title,txt.value,result,loginViewModel)
//            }) {
//                Icon(Icons.Filled.Send, contentDescription = "")
//            }
//        }
//    }
//    Text(text = result.value)

/*fun ChatRoomScreen(navController: NavController,loginViewModel: LoginViewModel) {
    val txt = remember{mutableStateOf("")}
//    val txt = loginViewModel.text
    val title = loginViewModel.user_name
    val ctx = LocalContext.current
    val result = remember {
        mutableStateOf("")
    }
    val created = remember {
        mutableStateOf("")
    }
    LazyColumn(modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(0.9f)){
        itemsIndexed(loginViewModel.chatList){index, item ->
            if (item.sender_username == loginViewModel.user_name) {
                Box(modifier = Modifier.padding(start = 90.dp) ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(16.dp),
                        backgroundColor = senderColor,

                    ) {
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                            Text(
                                text = item.text,
                                modifier = Modifier
                                    .padding(8.dp)
                                    // .background(Color(0xFFDCF8C6))
                                    //.border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            )
                            Text(
                                text = item.created.substring(12, 16),
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(end = 8.dp, bottom = 4.dp)
                            )
                        }
                    }
                }


            } else {
                Box(modifier = Modifier.padding(end = 90.dp)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(16.dp),
                        backgroundColor = Color.White
                    ) {
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                            Text(
                                text = item.text, modifier = Modifier
                                    .padding(8.dp)
                                    //                                .background(Color.White)
                                    //                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            )
                            Text(
                                text = item.created.substring(12, 16), modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(start = 8.dp, bottom = 4.dp, end = 5.dp)
                            )
                        }
                    }
                }
            }
        }
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
}*/



