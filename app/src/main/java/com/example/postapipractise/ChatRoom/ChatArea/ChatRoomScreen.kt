package com.example.postapipractise.ChatRoom

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.postapipractise.WebSocket.ChatWebSocket
import com.example.postapipractise.Login.ViewModel.LoadingView
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Message.MessageModel.MessageDataClass
import com.example.postapipractise.TypingStatus.TypingModel
import com.example.postapipractise.ui.theme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter",
    "RememberReturnType"
)
@Composable
fun ChatRoomScreen(navController: NavController,loginViewModel: LoginViewModel,chatWebSocket: ChatWebSocket) {
//    val title = loginViewModel.user_name
    val ctx = LocalContext.current
    val result = remember {
        mutableStateOf("")
    }
    val messageListState = loginViewModel.messageList.collectAsState()
    val messageList = messageListState.value
    Text(text = messageList.size.toString())

    var textFieldValue by remember { mutableStateOf("") }
    val isTextFieldEmpty = textFieldValue.isEmpty()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if(loginViewModel.istyping.value&&loginViewModel.user_name.value!=loginViewModel.istypinguser.value){
                        Text(text = "${loginViewModel.istypinguser.value} is typing..", fontStyle = FontStyle.Italic)
                        loginViewModel.starttyping()
                    }
                    else{
                        println("##@$$$$#$$@@$%%%%%%${loginViewModel.istypinguser.value}")
                    Text(text = if(loginViewModel.user_name.value == "Admin") " " else "Admin")
                    }
                },

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }, backgroundColor = Purple500
            )
        }
    ){
        if (loginViewModel.chatList.size!=0){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
//                .padding(bottom=0.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.White, Purple500),
                            startY = 1000f,
                            endY = 4000f
                        )
                    ),
                reverseLayout = true
            ) {
                itemsIndexed(loginViewModel.chatList.sortedByDescending { it.created }) { index, item ->
                    val date = item.created.subSequence(8, 10)
                    var month = item.created.subSequence(5, 7)
                    val year = item.created.subSequence(0, 4)
                    if (item.sender_username == loginViewModel.user_name.value) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End,
                        ) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 40.dp,
                                    top = 8.dp,
                                    end = 8.dp,
                                    bottom = 35.dp
                                )
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .clip(RoundedCornerShape(15.dp, 0.dp, 15.dp, 15.dp)),
                                    backgroundColor = senderColor,

                                    ) {
                                    Column(
//                                     Modifier.fillMaxWidth(),
                                        // horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = item.text,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .align(Alignment.End)
                                        )
                                        Text(
                                            text = "$date-$month-$year",
                                            modifier = Modifier
                                                .align(Alignment.End)
                                                .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                                        )
                                        Text(
                                            text = item.created.substring(12, 16),
                                            modifier = Modifier
                                                .align(Alignment.End)
                                                .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                                        )

                                    }
                                }
                            }
                        }


                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Box(
                                modifier = Modifier.padding(
                                    start = 10.dp,
                                    top = 8.dp,
                                    end = 8.dp,
                                    bottom = 35.dp
                                )
                            ) {
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
                                                .align(Alignment.End)
                                        )
                                        Text(
                                            text = "$date-$month-$year",
                                            modifier = Modifier
                                                .align(Alignment.End)
                                                .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                                        )
                                        Text(
                                            text = item.created.substring(12, 16),
                                            modifier = Modifier
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
        }
        else{
            Box(modifier = Modifier.fillMaxSize(1f), contentAlignment = Alignment.Center) {
                Card(modifier = Modifier.padding(16.dp), elevation = 8.dp) {
                    Text(text = "No Previous Chat", fontSize = 24.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = textFieldValue,
                maxLines = 2,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    IsTypingHelpingFunction(ctx,loginViewModel)
                },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Type Your Message Here") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Magenta,
                    focusedBorderColor = Purple500,
                    cursorColor = Purple500
                )
            )
            IconButton(
                onClick = {
                    chatWebSocket.sendMessage(textFieldValue)
                    postSenderMessage(
                        ctx,
//                        title.value,
                        textFieldValue,
                        result,
                        loginViewModel
                    )
                    textFieldValue = ""
//                        println( "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ChatRoomScreen: ${loginViewModel.chatList[loginViewModel.chatList.size-1]} ")
                },
                enabled = !isTextFieldEmpty
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "",
                    tint = if (isTextFieldEmpty) Color.Gray else Purple500 // Change the icon color based on whether the text field is empty or not
                )
            }
        }


    }
    if (loginViewModel.isLoading.value == true){
        LoadingView()
    }
//        Text(text = result.value)
    }



private fun postSenderMessage(
    ctx: Context,
//    title:String,
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

fun IsTypingHelpingFunction(
    context: Context,
    loginViewModel: LoginViewModel
)
{
    val retrofitAPI= loginViewModel.IsUserTyping()
    val call: Call<TypingModel?>? = retrofitAPI.notifyTyping()
    call!!.enqueue(object : Callback<TypingModel?> {
        override fun onResponse(call: Call<TypingModel?>, response: Response<TypingModel?>) {
            val model: TypingModel? = response.body()
            val resp =
                "Response Code : " + response.code()
//            loginViewModel.result=resp
        }
        override fun onFailure(call: Call<TypingModel?>, t: Throwable) {
            var temp = "Error found is : " + t.message
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()
        }
    })
}



