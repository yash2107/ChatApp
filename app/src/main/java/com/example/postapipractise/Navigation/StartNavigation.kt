package com.example.postapipractise.Navigation

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.postapipractise.ChatRoom.ChatRoomScreen
import com.example.postapipractise.ChatRoom.History
import com.example.postapipractise.ChatWebSocket
import com.example.postapipractise.Login.LoginScreen.LoginScreen
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.QuestionRoom.QuestionList
import com.example.postapipractise.QuestionRoom.QuestionListing
import com.example.postapipractise.QuestionRoom.QuestionViewModel


import com.example.postapipractise.Signup.Model.View.SignUpScaff
import com.example.postapipractise.Signup.Model.View.SignupPostData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.WebSocket

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartNavigation(sharedPreferences: SharedPreferences,navController: NavHostController = rememberNavController()){
    val loginViewModel:LoginViewModel = viewModel()
    val questionViewModel:QuestionViewModel = viewModel()
    val chatWebSocket = ChatWebSocket(loginViewModel)
     LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO){
            QuestionListing(questionViewModel)
        }
    }
//    ChatWebSocket(loginViewModel)
    NavHost(navController = navController, startDestination = NavigationId.LoginScreen.route){
        composable(NavigationId.LoginScreen.route){
            LoginScreen(navController,loginViewModel,sharedPreferences)
        }

        composable(NavigationId.SignUpScaff.route){
            SignUpScaff(navController)
        }
        composable(NavigationId.History.route){
            History(navController,loginViewModel,sharedPreferences)
        }
        composable(NavigationId.ChatRoomScreen.route){
            ChatRoomScreen( navController,loginViewModel,chatWebSocket)
        }
        composable(NavigationId.QuestionList.route){
            QuestionList(questionViewModel,loginViewModel,navController)
        }
    }
}