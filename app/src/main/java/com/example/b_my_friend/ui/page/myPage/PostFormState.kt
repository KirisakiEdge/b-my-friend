package com.example.b_my_friend.ui.page.myPage

data class PostFormState(
    val titleError: Int? = null,
    val textError: Int? = null,
    val isDataValid: Boolean = false
)