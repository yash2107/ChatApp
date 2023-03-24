package com.example.postapipractise.Signup.Model.View

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.postapipractise.Navigation.NavigationId
import com.example.postapipractise.Signup.Model.DataModel.DataModel
import com.example.postapipractise.Signup.Model.Network.RetrofitAPI
import com.example.postapipractise.ui.theme.LightPurple
import com.example.postapipractise.ui.theme.Purple200
import com.example.postapipractise.ui.theme.Purple700
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignupPostData(navController: NavController) {
    val ctx = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }

    val firstName = remember {
        mutableStateOf(TextFieldValue())
    }
    val lastName = remember {
        mutableStateOf(TextFieldValue())
    }
    val password = remember{
        mutableStateOf(TextFieldValue())
    }
    val response = remember {
        mutableStateOf("")
    }
    // on below line we are creating a column.
    Box(
        modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(
            colors = listOf(Color.White, Purple200),
            startY = 500f,
            endY = 3500f
        )),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .padding(16.dp),
            backgroundColor = LightPurple,
            shape = RoundedCornerShape(50.dp),
            elevation = 8.dp
        ) {
            Column(
                // on below line we are adding a modifier to it
                // and setting max size, max height and max width
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // on below line we are creating a text
                Text(
                    text = "Sign Up",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                //on below line we are adding spacer
                Spacer(modifier = Modifier.height(5.dp))
                // on below line we are creating a text field for our email.
                OutlinedTextField(
                    // on below line we are specifying value for our email text field.
                    value = userName.value,
                    // on below line we are adding on value change for text field.
                    onValueChange = { userName.value = it },
                    // on below line we are adding place holder as text as "Enter your email"
                    label = { Text(text = "Username") },
                    leadingIcon = {
                        Icon(Icons.Filled.Person, contentDescription = "Username icon",tint = Purple700)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Purple700,
                        focusedBorderColor = Purple700,
                        unfocusedBorderColor = LightPurple
                    )
                )
                // on below line we are adding spacer
                Spacer(modifier = Modifier.height(5.dp))
                // on below line we are creating a text field for our email.
                OutlinedTextField(
                    // on below line we are specifying value for our email text field.
                    value = firstName.value,
                    // on below line we are adding on value change for text field.
                    onValueChange = { firstName.value = it },
                    // on below line we are adding place holder as text as "Enter your email"
                    label = { Text(text = "First Name") },
                    leadingIcon = {
                        Icon(Icons.Filled.Info, contentDescription = "Username icon",tint = Purple700)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Purple700,
                        focusedBorderColor = Purple700,
                        unfocusedBorderColor = LightPurple
                    )
                )
                OutlinedTextField(
                    // on below line we are specifying value for our email text field.
                    value = lastName.value,
                    // on below line we are adding on value change for text field.
                    onValueChange = { lastName.value = it },
                    // on below line we are adding place holder as text as "Enter your email"
                    label = { Text(text = "Last Name") },
                    leadingIcon = {
                        Icon(Icons.Filled.Info, contentDescription = "Username icon",tint = Purple700)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Purple700,
                        focusedBorderColor = Purple700,
                        unfocusedBorderColor = LightPurple
                    )
                )
                OutlinedTextField(
                    // on below line we are specifying value for our email text field.
                    value = password.value,
                    // on below line we are adding on value change for text field.
                    onValueChange = { password.value = it },
                    // on below line we are adding place holder as text as "Enter your email"
                    label = { Text(text = "Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password icon",tint = Purple700)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Purple700,
                        focusedBorderColor = Purple700,
                        unfocusedBorderColor = LightPurple
                    )
                )
                // on below line we are adding spacer
                Spacer(modifier = Modifier.height(10.dp))
                // on below line we are creating a button
                Button(
                    onClick = {
                        // on below line we are calling make payment method to update data.
                        postDataUsingRetrofit(
                            ctx, userName, firstName, lastName, password, response
                        )
                        navController.navigate(NavigationId.LoginScreen.route)

                    },
                    // on below line we are adding modifier to our button.
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Purple700,
                        contentColor = Color.White
                    )
                ) {
                    // on below line we are adding text for our button
                    Text(text = "Post Data", modifier = Modifier.padding(8.dp))
                }
                // on below line we are adding a spacer.
                Spacer(modifier = Modifier.height(20.dp))
                // on below line we are creating a text for displaying a response.
                Text(
                    text = response.value,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


private fun postDataUsingRetrofit(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    firstName: MutableState<TextFieldValue>,
    lastName: MutableState<TextFieldValue>,
    password: MutableState<TextFieldValue>,
    result: MutableState<String>
) {
    val retrofitAPI = RetrofitAPI.postInstance()
    Toast.makeText(ctx,"I got Clicked",Toast.LENGTH_SHORT).show()
    Log.d("MYTAG","Chala################################################################")
    val dataModel = DataModel(userName.value.text, firstName.value.text,lastName.value.text,password.value.text, email = "")
    // calling a method to create an update and passing our model class.
    val call: Call<DataModel?>? = retrofitAPI?.postData(dataModel)
    // on below line we are executing our method.
    call!!.enqueue(object : Callback<DataModel?> {
        override fun onResponse(call: Call<DataModel?>?, response: Response<DataModel?>) {
            // this method is called when we get response from our api.
            Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()
            // we are getting a response from our body and
            // passing it to our model class.
            val model: DataModel? = response.body()
            // on below line we are getting our data from model class
            // and adding it to our string.
            val resp =
                "Response Code : " + response.code() /*+ "\n" + "User Name : " + model!!.username + "\n" + "Job : " + model!!.first_name*/
            // below line we are setting our string to our response.
            result.value = resp
        }

        override fun onFailure(call: Call<DataModel?>?, t: Throwable) {
            // we get error response from API.
            result.value = "Error found is : " + t.message
        }
    })

}
