package com.example.postapipractise.ChatRoom.ChatHistory

import com.example.postapipractise.GetAllChats.ChatDataModel.GetChatsDataClass
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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