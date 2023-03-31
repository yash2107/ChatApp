package com.example.postapipractise.Login.LoginScreen

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postapipractise.ChatRoom.ChatHistory.getChatHistory
import com.example.postapipractise.Login.LoginDataClass.LoginData
import com.example.postapipractise.Login.LoginState
import com.example.postapipractise.Login.ViewModel.*
import com.example.postapipractise.Navigation.NavigationId
import com.example.postapipractise.R
import com.example.postapipractise.ui.theme.LightPurple
import com.example.postapipractise.ui.theme.Purple200
import com.example.postapipractise.ui.theme.Purple700
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



@Composable
fun LoginScreen(navController:NavController,loginViewModel: LoginViewModel,sharedPreferences:SharedPreferences){

    val Username = loginViewModel.user_name
    val Password = loginViewModel.password

    val result = remember{ mutableStateOf("") }
    val secret = remember{ mutableStateOf("") }

    val isFieldsFilled = Username.value.isNotBlank() && Password.value.isNotBlank()

//    var loginState by remember { mutableStateOf<LoginState>(LoginState.Loading) }xl
    val ctx = LocalContext.current

    //Enable Shared Prefrences
    val un = sharedPreferences.getString("USERNAME", "").toString()
    val secrett = sharedPreferences.getString("SECRET", "").toString()

    if (un.isNotBlank()){
        loginViewModel.user_name.value = un
        loginViewModel.password.value = secrett
//        navController.navigate(NavigationId.History.route)
        getDetails(ctx,un,secrett,result,secret,navController,loginViewModel,sharedPreferences)
    }
    else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Purple200),
                        startY = 500f,
                        endY = 3500f
                    )
                ),
            contentAlignment = Alignment.Center
        ){
//        Image(painter = painterResource(id = R.drawable.logo ) , contentDescription = "", modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.FillBounds)
            Card (modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)

                .padding(16.dp),
                backgroundColor = LightPurple,
                shape = RoundedCornerShape(50.dp),
                elevation = 8.dp){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "",modifier = Modifier.size(160.dp))
                    Text(
                        text = "Nye Interactive Assistant",
                        color = Color.Blue,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    Text(
                        text = "Sign in to continue",
                        color = Color.Blue,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    //TextField to Enter Username
                    OutlinedTextField(
                        value = Username.value,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Purple700,
                            focusedBorderColor = Purple700,
                            unfocusedBorderColor = LightPurple
                        ),
                        onValueChange = { Username.value = it },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Username Icon",
                                tint = Purple700
                            )
                        },
                        label = { Text(text = "Username") },
                        placeholder = { Text(text = "Enter your username") },
                        textStyle = TextStyle(color = Purple700)
                    )

                    //TextField for entering Password
                    OutlinedTextField(
                        value = Password.value,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Purple700,
                            focusedBorderColor = Purple700,
                            unfocusedBorderColor = LightPurple
                        ),
                        onValueChange = { Password.value = it },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Password Icon",
                                tint = Purple700
                            )
                        },
                        label = { Text(text = "Password") },
                        placeholder = { Text(text = "Enter your password") },
                        visualTransformation = PasswordVisualTransformation(),
                        textStyle = TextStyle(color = Purple700)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    loginViewModel.user_name.value = Username.value
                    loginViewModel.password.value = Password.value
                    Button(modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Purple700,
                            contentColor = Color.White
                        ),
                        onClick = {
                            loginViewModel.isLoading.value = true
                            getDetails(ctx,Username.value,Password.value,result,secret,navController,loginViewModel,sharedPreferences)
                        }, enabled = isFieldsFilled) {
                        Text(text = "Login")
                    }
                    Spacer(modifier = Modifier.height(5.dp))

//                when (loginState) {
//                    LoginState.Loading -> CircularProgressIndicator()
//                    is LoginState.Success -> Text("Logged in as ${(loginState as LoginState.Success).data.username}")
//                    is LoginState.Error -> Text((loginState as LoginState.Error).message ?: "An error occurred")
//                    null -> Unit
//                }
                    Row {
                        Text(text = "Don't Have Account?")
                        Text(text = "SignUp", color = Color.Blue,
                            modifier = Modifier.clickable {
                                navController.navigate(NavigationId.SignUpScaff.route)
                            })
                    }
                    if (loginViewModel.isLoading.value==true){
                        LoadingView()
                    }
                }
            }

        }
    }
}


private fun getDetails(
    /*Function to authenticate user and retrieve user details from API using Retrofit library and update viewmodel and shared preferences accordingly.*/
    ctx: Context,
    Username:String,
    Password:String,
    result: MutableState<String>,
    secret: MutableState<String>,
    navController: NavController,
    loginViewModel: LoginViewModel,
    sharedPreferences: SharedPreferences
){
    val authenticationApi = loginViewModel.AuthenticateUser()

    val call: Call<LoginData?>? = authenticationApi.getUsers()

    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    call!!.enqueue(object : Callback<LoginData?> {

        override fun onResponse(call: Call<LoginData?>?, response: Response<LoginData?>) {
//            Toast.makeText(ctx, "Logged in", Toast.LENGTH_SHORT).show()
            val model: LoginData? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n"+"Id: " + model?.is_authenticated+  "\n"+ model?.username
            result.value = resp
            secret.value = model?.secret.toString()
            loginViewModel.UserData=model // Set the user data in the ViewModel
            /*Check if user is authenticated, navigate to History screen if true, else show toast message and set isLoading to false.*/
            if(model?.is_authenticated==true){
                navController.navigate(NavigationId.History.route)
                Toast.makeText(ctx,"Logged in Correctly",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(ctx,"No user Found",Toast.LENGTH_SHORT).show()
                loginViewModel.isLoading.value = false
            }
            loginViewModel.isLoading.value = false
            println("/////////////////////////////////////////////////////${secret.value}")
            if (response.isSuccessful){
                getChatHistory(loginViewModel)
                editor.putString("USERNAME", Username)
                editor.putString("SECRET", Password)
                editor.apply()
            }
        }
        override fun onFailure(call: Call<LoginData?>?, t: Throwable) {
            result.value ="error "+t.message
        }
    })

}



