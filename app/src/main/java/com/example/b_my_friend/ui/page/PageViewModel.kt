package com.example.b_my_friend.ui.page


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.data.model.UserViewModel

class PageViewModel(val user: User = User()): ViewModel() {


    private val mutableData = MutableLiveData<User>()

    fun setDataUser(user: User){
        mutableData.postValue(user)
    }

    fun getCurrentUser(): LiveData<User>{
        return mutableData
    }


}