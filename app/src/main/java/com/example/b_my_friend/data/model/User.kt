package com.example.b_my_friend.data.model

import com.example.b_my_friend.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    @Expose
    var id: String = "",

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("email")
    @Expose
    var email: String = "",

    @SerializedName("email_verified_at")
    @Expose
    var emailVerifiedAt: Any? = null,

    var avatar: Int = R.mipmap.ic_launcher_round){


    override fun toString(): String {
        return "name = $name, email = $email"
    }
}