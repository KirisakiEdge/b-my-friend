package com.example.b_my_friend.ui.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.networking.Count

class PageViewModel(val user: User = User()): ViewModel() {


    private val loggedUser = MutableLiveData<User>()
    fun setDataUser(user: User) = loggedUser.postValue(user)
    fun getCurrentUser(): LiveData<User> { return loggedUser }


    private val followCount = MutableLiveData<List<Count>>()
    fun setFollow(followers: Count, followings: Count)= followCount.postValue(listOf(followers, followings))
    fun getFollowUser(): LiveData<List<Count>> { return followCount }


    private val myFollowings = MutableLiveData<MutableList<User>>()
    fun setMyFollowings(followings: MutableList<User>)= myFollowings.postValue(followings)
    fun getMyFollowings(): LiveData<MutableList<User>> { return myFollowings }
}