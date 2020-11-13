package com.example.b_my_friend.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false,
    val codeError: Int? = null,
    val newPasswordError: Int? = null,
    val passwordConfirmError: Int? = null
)