package com.example.postapipractise.Login.LoginScreen

import android.content.Context
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
import com.example.postapipractise.Login.LoginDataClass.LoginData
import com.example.postapipractise.Login.LoginState
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Navigation.NavigationId
import com.example.postapipractise.R
import com.example.postapipractise.ui.theme.LightPurple
import com.example.postapipractise.ui.theme.Purple200
import com.example.postapipractise.ui.theme.Purple700
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController:NavController,loginViewModel: LoginViewModel){

    var Username = remember{ mutableStateOf("") }
    var Password = remember{ mutableStateOf("") }

    var result = remember{ mutableStateOf("") }
    var secret = remember{ mutableStateOf("") }

    val isFieldsFilled = Username.value.isNotBlank() && Password.value.isNotBlank()

//    var loginState by remember { mutableStateOf<LoginState>(LoginState.Loading) }
    val ctx = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(
            colors = listOf(Color.White, Purple200),
            startY = 500f,
            endY = 3500f
        )),
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
//                Spacer(modifier = Modifier.height(10.dp))
//                Text(
//                    text = "(NIA)",
//                    color = Color.Blue,
//                    fontSize = 24.sp,
//                    fontFamily = FontFamily.SansSerif,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.padding(vertical = 4.dp)
//                )
                Text(
                    text = "Sign in to continue",
                    color = Color.Blue,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

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

                loginViewModel.user_name = Username.value
                loginViewModel.password = Password.value
                Button(modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Purple700,
                        contentColor = Color.White
                    ),
                    onClick = {
                        getDetails(ctx,result,secret,navController,loginViewModel)
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
            }
        }
    }
}


private fun getDetails(
    ctx: Context,
    result: MutableState<String>,
    secret: MutableState<String>,
    navController: NavController,
    loginViewModel: LoginViewModel
){
    val authenticationApi = loginViewModel.AuthenticateUser()

    val call: Call<LoginData?>? = authenticationApi.getUsers()


    call!!.enqueue(object : Callback<LoginData?> {

        override fun onResponse(call: Call<LoginData?>?, response: Response<LoginData?>) {
//            Toast.makeText(ctx, "Logged in", Toast.LENGTH_SHORT).show()
            val model: LoginData? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n"+"Id: " + model?.is_authenticated+  "\n"+ model?.username
            result.value = resp
            secret.value = model?.secret.toString()
            loginViewModel.UserData=model
            if(model?.is_authenticated==true){
//                navController.navigate(NavigationItems.UserScreen.route)
                navController.navigate(NavigationId.History.route)
                Toast.makeText(ctx,"Logged in Correctly",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(ctx,"No user Found",Toast.LENGTH_LONG).show()
            }
            println("/////////////////////////////////////////////////////${secret.value}")

        }

        override fun onFailure(call: Call<LoginData?>?, t: Throwable) {
            result.value ="error "+t.message
        }
    })

}



