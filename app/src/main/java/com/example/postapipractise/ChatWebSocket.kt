package com.example.postapipractise


import android.util.Log
import androidx.compose.runtime.*
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import okhttp3.*
import okio.ByteString


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

