package com.example.postapipractise.QuestionRoom

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postapipractise.ChatRoom.ChatHistory.getChatHistory
import com.example.postapipractise.ChatRoom.postChatRoom
import com.example.postapipractise.Login.ViewModel.LoginViewModel
import com.example.postapipractise.Navigation.NavigationId
import com.example.postapipractise.ui.theme.Purple500

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuestionList(questionViewModel: QuestionViewModel,loginViewModel: LoginViewModel,navController:NavController) {
    val expandedQuestion = remember { mutableStateOf<Question?>(null) }

    val questions by questionViewModel.questions.observeAsState(emptyList())

    val ctx = LocalContext.current
    var title by remember {
        mutableStateOf("")
    }
    val scaffoldState = rememberScaffoldState()
    val result = remember { mutableStateOf("") }
//    var isTalkToAgentVisible by remember { mutableStateOf(false) }



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Ask Me")
                },

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }, backgroundColor = Purple500
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    postChatRoom(ctx, title, result, loginViewModel, navController)
//                    getChatHistory(loginViewModel)
                }
            ) {
                Icon(Icons.Filled.Person, contentDescription = "")
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(questions) { question ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            expandedQuestion.value = question
                        },
                    elevation = 8.dp

                )
                { println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@${title}")
                    Text(
                        text = question.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
//            }

                if (expandedQuestion.value == question) {
                    question.subQuestions.forEach { subQuestion ->
                        loginViewModel.title = subQuestion.question
                        SubQuestion(
                            subQuestion = subQuestion,
                            onSubQuestionClick = { expandedQuestion.value = question },
                            loginViewModel
                        )
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            questionViewModel.loadQuestions()
        }
    }
}



@Composable
fun SubQuestion(
    subQuestion: SubQuestion,
    onSubQuestionClick: () -> Unit,
    loginViewModel: LoginViewModel
) {
    var expandedSubQuestion by remember { mutableStateOf(false) }

    if (subQuestion.subQuestions?.isEmpty() == true) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clickable { onSubQuestionClick() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Solution",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = subQuestion.question,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.weight(1f)
            )
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clickable { expandedSubQuestion = !expandedSubQuestion },
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = subQuestion.question,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (expandedSubQuestion) {
                    subQuestion.subQuestions?.forEach { nestedSubQuestion ->
                        loginViewModel.title = subQuestion.question
                        SubQuestion(
                            subQuestion = nestedSubQuestion,
                            onSubQuestionClick = onSubQuestionClick,
                            loginViewModel
                        )
                    }
                }
            }
        }
    }
}
