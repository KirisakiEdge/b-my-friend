package com.example.b_my_friend.ui.page.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.b_my_friend.R

class PostViewModel : ViewModel() {

    private val _loginForm = MutableLiveData<PostFormState>()
    val postFormState: LiveData<PostFormState> = _loginForm

    fun newPost(title: String, text: String){
        when {
            (title.isEmpty())-> {
                _loginForm.value = PostFormState(titleError = R.string.fillInFiled)
            }
            ( text.isEmpty()) ->{
                _loginForm.value = PostFormState(textError = R.string.fillInFiled)
            }
            else -> {
                _loginForm.value = PostFormState(isDataValid = true)
            }
        }
    }
}