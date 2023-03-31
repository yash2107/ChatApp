package com.example.postapipractise.ChatRoom

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.postapipractise.ChatRoom.DataModel.ChatRoomDataModel
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Navigation.NavigationId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun postChatRoom(
    ctx: Context,
    title:String,
    result: MutableState<String>,
    loginViewModel: LoginViewModel,
    navController: NavController
){
    val chatRoomApi = loginViewModel.createRoom()
    val chatRoomDataModel = ChatRoomDataModel(loginViewModel.title,false, listOf("Admin"))

    val call: Call<ChatRoomDataModel?>? =chatRoomApi.postChatRoom(chatRoomDataModel)

    call!!.enqueue(object: Callback<ChatRoomDataModel?> {
        override fun onResponse(
            call: Call<ChatRoomDataModel?>,
            response: Response<ChatRoomDataModel?>
        ) {
            Toast.makeText(ctx,"Room Created", Toast.LENGTH_SHORT).show()
            val model: ChatRoomDataModel? = response.body()
            val resp = "Response Code: " +response.body() + "\n"+ "Title:" +model?.title + "\n"+ model?.is_direct_chat
            result.value = resp
            loginViewModel.chatData=model
            if(model?.is_direct_chat==false){
                navController.navigate(NavigationId.History.route)
                Toast.makeText(ctx,"Chat in", Toast.LENGTH_LONG).show()
            }

        }

        override fun onFailure(call: Call<ChatRoomDataModel?>, t: Throwable) {
            result.value = "Error "+ t.message
        }

    }
    )
}