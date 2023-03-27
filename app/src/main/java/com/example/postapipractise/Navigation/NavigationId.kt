package com.example.postapipractise.Navigation

sealed class NavigationId(val route:String){
    object LoginScreen:NavigationId("login_screen")

    object SignupPostData:NavigationId("sign_up")

    object SignUpScaff:NavigationId("sign_up_scaff")

    object History:NavigationId("history")

    object ChatRoomScreen:NavigationId("chat_room_screen")

    object QuestionList:NavigationId("question_list")
}
