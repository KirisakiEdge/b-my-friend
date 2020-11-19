package com.example.b_my_friend.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.b_my_friend.R


class LoginViewModel(val context: Context) : ViewModel() {


    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun registerDataChanged(username: String, email: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        }else if(!isEmailValid(email)){
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)){
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun emailVerify(email: String){
        if (!isEmailValid(email)){
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun resetCode(code: String){
        if (code.length < 7){
            _loginForm.value = LoginFormState(codeError = R.string.invalidCode)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun resetPassword(newPassword: String, passwordConfirm: String){
        when {
            newPassword.length < 5 -> {
                _loginForm.value = LoginFormState(newPasswordError = R.string.invalidNewPassword)
            }
            passwordConfirm != newPassword -> {
                _loginForm.value = LoginFormState(passwordConfirmError = R.string.invalidPasswordConfirm)
            }
            else -> {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }



    private fun isUserNameValid(username: String): Boolean {
        return username.length > 5
    }

    // A placeholder username validation check
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}
