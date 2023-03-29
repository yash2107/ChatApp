package com.example.postapipractise.ChatRoom

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.GetAllChats.ChatDataModel.GetChatsDataClass
import com.example.postapipractise.GetAllChats.ChatDataModel.People
import com.example.postapipractise.Login.ViewModel.LoadingView
//import com.example.postapipractise.ChatWebSocket
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.MainActivity
import com.example.postapipractise.Message.MessageDataClass
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import com.example.postapipractise.Navigation.NavigationId
import com.example.postapipractise.R


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
/*@Composable
fun History(navController: NavController,loginViewModel: LoginViewModel) {
//    val chatWebSocket = ChatWebSocket(loginViewModel)
    val title = loginViewModel.user_name
    println("################################# $title")
    val ctx = LocalContext.current

    val result = remember {
        mutableStateOf("")
    }
    val resultResponse = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val sharedPreferences = LocalContext.current.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    sharedPreferences.edit().putString("secret_id", loginViewModel.password).apply()

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
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) { // Use NavHostController to handle navigation back
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {



                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "LogOut")
                    }
                }
                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                postChatRoom(ctx,title,result,loginViewModel,navController)
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
                                    loginViewModel.isLoading.value=true
                                    loginViewModel.chatId = item.id
                                    loginViewModel.accesskey = item.access_key
//                                        navController.navigate(NavigationId.ChatRoomScreen.route)
                                        getSendMessage(resultResponse,loginViewModel,navController)
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


        *//*Column() {
            Button(modifier = Modifier.padding(start = 130.dp, top = 150.dp),
                onClick = {
                    navController.navigate(NavigationId.ChatRoomScreen.route)
                    getSendMessage(resultResponse, loginViewModel)
                }) {

                Text(text = "${loginViewModel.user_name} Chat room")

            }
        }
        Text(text = title)
        Text(text = result.value)*//*

    }
    if (loginViewModel.isLoading.value == true){
        Text(text = "this is inside text")
        println("*******************************************8")
        LoadingView()
    }
}*/


@Composable
fun History(navController: NavController, loginViewModel: LoginViewModel,sharedPreferences: SharedPreferences) {
    val ctx = LocalContext.current
    val title = loginViewModel.user_name
    val scaffoldState = rememberScaffoldState()
    val result = remember { mutableStateOf("") }
    val resultResponse = remember { mutableStateOf("") }
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    getChatHistory(loginViewModel)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Chats") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "LogOut")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    postChatRoom(ctx, title, result, loginViewModel, navController)
                    navController.navigate(NavigationId.QuestionList.route)
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            itemsIndexed(loginViewModel.allChats) { _, item ->
                val time = item.created.subSequence(11, 16)
                val cardName = if (title == "user1") "user2" else "user1"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable (onClick = {
                            loginViewModel.chatId = item.id
                            loginViewModel.accesskey = item.access_key
                            navController.navigate(NavigationId.ChatRoomScreen.route)
                            loginViewModel.isLoading.value=true
                            getSendMessage(resultResponse,loginViewModel,navController)
                        }
                        )
                        ,
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.White
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text =  cardName,    /*item.people.firstOrNull()?.person?.username ?: ""*/
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.last_message.text,
                                style = MaterialTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Gray
                            )
                        }
                        Text(
                            text = time.toString(),
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
        if (loginViewModel.isLoading.value == true){
            LoadingView()
        }
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
    loginViewModel: LoginViewModel,
    navController: NavController
){
    val getMessageApi = loginViewModel.receiveMessage()
    val call: Call<List<ReceiveDataClass>?>? = getMessageApi.getMessage()

    call!!.enqueue(object :Callback<List<ReceiveDataClass>?>{
        override fun onResponse(
            call: Call<List<ReceiveDataClass>?>,
            response: Response<List<ReceiveDataClass>?>
        ) {

            val model: List<ReceiveDataClass> = (response.body() ?: emptyList())
            if(model.isNotEmpty()){
                loginViewModel.chatList = model as MutableList<ReceiveDataClass>
            }
            loginViewModel.isLoading.value =false

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




fun getChatHistory(
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
            if (model.isNotEmpty()) {
                loginViewModel.allChats = model as MutableList<GetChatsDataClass>
            }


        }
        override fun onFailure(call: Call<List<GetChatsDataClass>?>, t: Throwable) {
            //getApiResult.value="error "+t.message
        }
    })
}