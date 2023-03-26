package com.example.postapipractise.Navigation

import androidx.compose.runtime.Composable
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
import com.example.postapipractise.Signup.Model.View.SignUpScaff
import com.example.postapipractise.Signup.Model.View.SignupPostData
import okhttp3.WebSocket

@Composable
fun StartNavigation(navController: NavHostController = rememberNavController()){
    val loginViewModel:LoginViewModel = viewModel()
//    val chatWebSocket = ChatWebSocket(loginViewModel)
    NavHost(navController = navController, startDestination = NavigationId.LoginScreen.route){
        composable(NavigationId.LoginScreen.route){
            LoginScreen(navController,loginViewModel)
        }

//        composable(NavigationId.SignupPostData.route){
//            SignupPostData(navController)
//        }

        composable(NavigationId.SignUpScaff.route){
            SignUpScaff(navController)
        }
        composable(NavigationId.History.route){
            History(navController,loginViewModel)
        }
        composable(NavigationId.ChatRoomScreen.route){
            ChatRoomScreen( navController,loginViewModel)
        }
    }
}