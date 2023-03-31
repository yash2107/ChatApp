package com.example.postapipractise.ChatRoom

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.GetAllChats.ChatDataModel.GetChatsDataClass
import com.example.postapipractise.Login.ViewModel.LoadingView
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.MainActivity
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import com.example.postapipractise.Navigation.NavigationId
import com.example.postapipractise.QuestionRoom.QuestionViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun History(navController: NavController, loginViewModel: LoginViewModel,sharedPreferences: SharedPreferences,questionViewModel: QuestionViewModel) {
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
                title = { Text(text = "Welcome ${loginViewModel.user_name.value}") },
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
                        editor.putString("USERNAME", "")
                        editor.putString("SECRET", "")
                        editor.apply()
                        val intent = Intent(ctx, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        ctx.startActivity(intent)
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "LogOut")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                    postChatRoom(ctx, title.value, result, loginViewModel, navController)
                    navController.navigate(NavigationId.QuestionList.route)
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
    ) {
        if (loginViewModel.allChats.size != 0) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                itemsIndexed(loginViewModel.allChats) { lastindex, item ->
                    val time = item.created.subSequence(11, 16)
//                        if (loginViewModel.user_name.value == loginViewModel) item.title else "user2"
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clickable(onClick = {
                                navController.navigate(NavigationId.ChatRoomScreen.route)
                                loginViewModel.isLoading.value = true
                                loginViewModel.chatId = item.id
                                loginViewModel.accesskey = item.access_key
                                getSendMessage(resultResponse, loginViewModel, navController)
                            }
                            ),
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
                                    text = item.title,
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
            if (loginViewModel.isLoading.value == true) {
                LoadingView()
            }
        }
        else{
            Box(
                modifier = Modifier.height(550.dp)
                    .width(800.dp)
                    .padding(top=180.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.LightGray
                        ,shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center,
            ){
                Text(text = "No Chats Available",
                    fontSize = 20.sp)
                if (loginViewModel.isLoading.value == true) {
                    LoadingView()
                }
            }
        }
    }
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

        }
    })
}