package com.example.postapipractise.ChatRoom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomApi
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import com.example.postapipractise.Navigation.NavigationId
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Modifier

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun History(navController: NavController,loginViewModel: LoginViewModel) {
    val title = loginViewModel.user_name
    println("################################# $title")
    val ctx = LocalContext.current

    val result = remember {
        mutableStateOf("")
    }

    val scaffoldState = rememberScaffoldState()
    Scaffold(

        scaffoldState = scaffoldState,
        topBar= {
            TopAppBar(
                title = {
                    Text(text = "Chats")
                },
                navigationIcon = {
                },

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                postChatRoom(ctx,title,result,loginViewModel,navController)
            },
            ) {
                Icon(Icons.Filled.Add, contentDescription ="")
            }
        },
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Button(onClick = {
                navController.navigate(NavigationId.ChatRoomScreen.route)
                getMessage(result,loginViewModel)
            }) {

                Text(text = "Start Chatting")

            }
        }
        Text(text = title)
        Text(text = result.value)

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

private fun getMessage(
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
            val model: List<ReceiveDataClass>? = response.body()
//            val resp = model.size.
//            if (resp != null) {
//                result.value = resp
//            }
            if (model != null) {
                loginViewModel.chatList= model
            }
        }


        override fun onFailure(call: Call<List<ReceiveDataClass>?>, t: Throwable) {
            result.value ="error "+t.message
        }

    })
}