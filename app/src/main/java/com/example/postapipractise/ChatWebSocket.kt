package com.example.postapipractise


import android.util.Log
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.net.SocketException



class ChatWebSocket(private val loginViewModel: LoginViewModel):WebSocketListener(){
    private var webSocket: WebSocket
    init {
        val request = Request.Builder().url("wss://api.chatengine.io/chat/?projectID=52690bdb-3b85-4b96-9081-27fa9b4dc10e&chatID=${loginViewModel.chatId}&accessKey=${loginViewModel.accesskey}").build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("MYTAG", "WebSocket connection established.")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("MYTAG", "onMessage: ${text}")




        val gson = Gson()
        val json = JSONObject(text)
        val action = json.getString("action")

        if (action == "new_message") {
            val message = json.getJSONObject("data").getJSONObject("message")
            val receivedMessage = ReceiveDataClass(
                text = message.getString("text"),
                created = message.getString("created"),
                sender_username = message.getString("sender_username")
            )
            loginViewModel.updateUIWithNewMessage(receivedMessage)
//            loginViewModel.chatList.add(receivedMessage)
            loginViewModel.updateMessageList((loginViewModel.messageList.value + message) as List<ReceiveDataClass>)
            Log.d("MYTAG", "onMessage: ${receivedMessage} ${loginViewModel.chatList.size} ")
            }
        if (action =="is_typing") {
            val data=json.getJSONObject("data")
            val name=data.getString("person")
            loginViewModel.istyping.value=true
            loginViewModel.istypinguser.value= name.toString()
        }
    }

    fun onClose(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("MYTAG", "WebSocket connection closed.")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        if (t is SocketException && t.message?.contains("Broken pipe") == true) {
            Log.d("MYTAG", "WebSocket failure: Broken pipe")
            // Reconnect the WebSocket here
            val request = Request.Builder()
                .url("wss://api.chatengine.io/chat/?projectID=52690bdb-3b85-4b96-9081-27fa9b4dc10e&chatID=${loginViewModel.chatId}&accessKey=${loginViewModel.accesskey}")
                .build()
            val client = OkHttpClient()
            this.webSocket = client.newWebSocket(request, this)
        }
        else{Log.d("MYTAG", "WebSocket failure.", t)}

    }

    fun sendMessage(message: String) {
        webSocket.send(message)
    }


    fun closeWebSocket() {
        webSocket.close(1000, "Closing WebSocket.")
    }
}
