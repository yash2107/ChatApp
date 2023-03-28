package com.example.postapipractise.ChatRoom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.GetAllChats.ChatDataModel.GetChatsDataClass
//import com.example.postapipractise.ChatWebSocket
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Message.MessageDataClass
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import com.example.postapipractise.Navigation.NavigationId


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun History(navController: NavController,loginViewModel: LoginViewModel) {
//    val chatWebSocket = ChatWebSocket(loginViewModel)
    val isLoading = remember { mutableStateOf(false) }
    val title = loginViewModel.user_name
    println("################################# $title")
    val ctx = LocalContext.current

    val result = remember {
        mutableStateOf("")
    }
    val resultResponse = remember {
        mutableStateOf("")
    }

    getChatHistory(loginViewModel)

    val scaffoldState = rememberScaffoldState()
    Scaffold(

        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Chats")
                },
                navigationIcon = {
                },

                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                postChatRoom(ctx,title,result,loginViewModel,navController)
                    navController.navigate(NavigationId.QuestionList.route)

                },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(690.dp)
                .background(Color.White)
                .padding(8.dp),

            //reverseLayout = true
            //.sortedByDescending{it.created}

        ){
            itemsIndexed(loginViewModel.allChats) { lastIndex, item ->

                val time = item.created.subSequence(11, 16)

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(3.dp)
                    ) {
                        Column(
                            Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.SpaceBetween

                        ) {
                            Row(
                                modifier = Modifier.padding(5.dp),

                                ) {
                                val cardName =
                                    if (title == "user1") "user2" else "user1"
//                                        if (item.people[lastIndex].person.username == "tarushi07") "yash07" else "tarushi07"
                                Text(
                                    text = cardName,
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(200.dp))
                                Text(
                                    text = time.toString(),
                                    style = MaterialTheme.typography.h6,
                                    color = Color.LightGray,
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                            ) {
                                Text(
                                    text = item.last_message.text,
                                    style = MaterialTheme.typography.h6,
                                    color = Color.LightGray
                                )
                                Spacer(modifier = Modifier.width(150.dp))
                                IconButton(onClick = {
                                    navController.navigate(NavigationId.ChatRoomScreen.route)
                                    getSendMessage(resultResponse,loginViewModel)
                                }) {
                                    Icon(
                                        Icons.Default.ArrowForward,
                                        contentDescription = "",
                                        tint = Color.Gray,
                                        //modifier = Modifier.align(Alignment.CenterEnd)
                                    )

                                }
                            }
                        }
                    }
                    Divider(Modifier.height(3.dp))
                }
            }
        }
        Text(
            text = result.value,
        )


        /*Column() {
            Button(modifier = Modifier.padding(start = 130.dp, top = 150.dp),
                onClick = {
                    navController.navigate(NavigationId.ChatRoomScreen.route)
                    getSendMessage(resultResponse, loginViewModel)
                }) {

                Text(text = "${loginViewModel.user_name} Chat room")

            }
        }
        Text(text = title)
        Text(text = result.value)*/

    }
}


private fun postChatRoom(
    ctx:Context,
    title:String,
    result: MutableState<String>,
    loginViewModel: LoginViewModel,
    navController:NavController
){
    val chatRoomApi = loginViewModel.createRoom()
    val chatRoomDataModel =ChatRoomDataModel(title,false)

    val call: Call<ChatRoomDataModel?>? =chatRoomApi.postChatRoom(chatRoomDataModel)

    call!!.enqueue(object: Callback<ChatRoomDataModel?>{
        override fun onResponse(
            call: Call<ChatRoomDataModel?>,
            response: Response<ChatRoomDataModel?>
        ) {
            Toast.makeText(ctx,"Room Created",Toast.LENGTH_SHORT).show()
            val model:ChatRoomDataModel? = response.body()
            val resp = "Response Code: " +response.body() + "\n"+ "Title:" +model?.title + "\n"+ model?.is_direct_chat
            result.value = resp
            loginViewModel.chatData=model
//            if(model?.is_direct_chat==false){
//                navController.navigate(NavigationId.ChatRoomScreen.route)
//                Toast.makeText(ctx,"Chat in",Toast.LENGTH_LONG).show()
//            }
            print("///////////////////////////////////$title")
        }

        override fun onFailure(call: Call<ChatRoomDataModel?>, t: Throwable) {
            result.value = "Error "+ t.message
        }

    }
    )
}

private fun getSendMessage(
    result: MutableState<String>,
    loginViewModel: LoginViewModel
){
    val getMessageApi = loginViewModel.receiveMessage()
    val call: Call<List<ReceiveDataClass>?>? = getMessageApi.getMessage()

    call!!.enqueue(object :Callback<List<ReceiveDataClass>?>{
        override fun onResponse(
            call: Call<List<ReceiveDataClass>?>,
            response: Response<List<ReceiveDataClass>?>
        ) {
            val model: List<ReceiveDataClass> = (response.body() ?: emptyList())
            loginViewModel.chatList = model as MutableList<ReceiveDataClass>
//            loginViewModel.updateUIWithNewMessage(message = MessageDataClass(result.value))
//            if (model != null) {
//                loginViewModel.chatList= model
//            }
        }

        override fun onFailure(call: Call<List<ReceiveDataClass>?>, t: Throwable) {
            result.value ="error "+t.message
        }

    }
    )
}




private fun getChatHistory(
//    context: Context,
//    getApiResult: MutableState<String>,
    loginViewModel: LoginViewModel
) {
    val retrofitAPI = loginViewModel.getAllChats()

    val call: Call<List<GetChatsDataClass>?>? = retrofitAPI.getChats()


    call!!.enqueue(object : Callback<List<GetChatsDataClass>?> {

        override fun onResponse(call: Call<List<GetChatsDataClass>?>, response: Response<List<GetChatsDataClass>?>) {
            //Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
            val model: List<GetChatsDataClass> = response.body()?: emptyList()

            loginViewModel.allChats= model as MutableList<GetChatsDataClass>
//            val resp =model
//                        getApiResult.value= resp.toString()
//            if (model != null) {
//                loginViewModel.firstMsgGet=model
//            }

        }

        override fun onFailure(call: Call<List<GetChatsDataClass>?>, t: Throwable) {
            //getApiResult.value="error "+t.message
        }
    })
}