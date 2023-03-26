package com.example.postapipractise


import android.util.Log
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Message.ReceiveMessage.ReceiveDataClass
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.net.SocketException

//class ChatWebSocket(loginViewModel: LoginViewModel):WebSocketListener(){
//    val webSocket = OkHttpClient().newWebSocket(
//        Request.Builder().url("wss://api.chatengine.io/person/?publicKey=52690bdb-3b85-4b96-9081-27fa9b4dc10e&username=${loginViewModel.user_name}&secret=${loginViewModel.password}").build(),
//        object : WebSocketListener() {
//            override fun onOpen(webSocket: WebSocket, response: Response) {
//                // Connection established
//                Log.d("MYTAG","&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Connection Established")
//
//            }
//
//            override fun onMessage(webSocket: WebSocket, text: String) {
//                // Parse the new message
//                val message = Gson().fromJson(text, MessageDataClass::class.java)
//                Log.d("MYTAG",text)
//                // Update the UI to display the new message
////                loginViewModel.updateUIWithNewMessage(text)
//
//            }
//
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//                // Handle the error
//
//            }
//
//        }
//    )
//}

class ChatWebSocket(private val loginViewModel: LoginViewModel):WebSocketListener(){
    private lateinit var webSocket: WebSocket

    init {
        val request = Request.Builder().url("wss://api.chatengine.io/person/?publicKey=52690bdb-3b85-4b96-9081-27fa9b4dc10e&username=${loginViewModel.user_name}&secret=${loginViewModel.password}").build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("MYTAG", "WebSocket connection established.")
//        webSocket.send("Hello, server!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
//        val message = Gson().fromJson(text, ReceiveDataClass::class.java)
//
//        Log.d("MYTAG", "Received message: $text")
//        // Update your UI with the new message
//        loginViewModel.updateUIWithNewMessage(message)
        /*super.onMessage(webSocket, text)
        Log.d("MYTAG", "Received text: $text")
        val receivedMessage = Gson().fromJson(text, ReceiveDataClass::class.java)
        if (receivedMessage != null) {
            loginViewModel.updateUIWithNewMessage(receivedMessage)

        }
        Log.d("MYTAG", "onMessage: $receivedMessage ")*/
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
            Log.d("MYTAG", "onMessage: $receivedMessage ")
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
                .url("wss://api.chatengine.io/person/?publicKey=52690bdb-3b85-4b96-9081-27fa9b4dc10e&username=${loginViewModel.user_name}&secret=${loginViewModel.password}")
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
//            override fun onOpen(webSocket: WebSocket, response: Response) {
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
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
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

