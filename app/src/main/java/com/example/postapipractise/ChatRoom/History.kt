package com.example.postapipractise.ChatRoom

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun History(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar= {
            TopAppBar(
                title = {
                    Text(text = "Chats")
                },
                navigationIcon = {

                },

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

            },
            ) {
                Icon(Icons.Filled.Add, contentDescription ="")
            }
        }
    ) {

    }
}