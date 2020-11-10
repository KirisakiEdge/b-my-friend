package com.example.b_my_friend.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val displayEmail: String
    //... other data fields that may be accessible to the UI
)