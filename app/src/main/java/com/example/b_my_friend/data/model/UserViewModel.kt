package com.example.b_my_friend.data.model

import com.example.b_my_friend.R
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