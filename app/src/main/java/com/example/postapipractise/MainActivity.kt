package com.example.postapipractise

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postapipractise.Navigation.StartNavigation
import com.example.postapipractise.QuestionRoom.QuestionList
import com.example.postapipractise.QuestionRoom.QuestionListing
import com.example.postapipractise.QuestionRoom.QuestionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : ComponentActivity() {
    lateinit var sharedPreferences:SharedPreferences
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        setContent {
            sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
            val username = remember {
                mutableStateOf("")
            }
            val pwd = remember {
                mutableStateOf("")
            }
            username.value = sharedPreferences.getString("USERNAME", "").toString()
            pwd.value = sharedPreferences.getString("SECRET", "").toString()
            StartNavigation(sharedPreferences)
//            QuestionList(questionViewModel)
        }
    }
}