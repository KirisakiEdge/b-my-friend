package com.example.b_my_friend.data.model

import com.example.b_my_friend.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserViewModel(
    var id: String,
    var name: String,
    var email: String,
    var emailVerifiedAt: Any?,
    var emailVerificationToken: Any?,
    var avatar: Int = R.mipmap.ic_launcher_round){

    override fun toString(): String {
        return "name = $name, email = $email"
    }
}