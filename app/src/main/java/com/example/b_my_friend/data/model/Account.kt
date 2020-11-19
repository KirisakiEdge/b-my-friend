package com.example.b_my_friend.data.model

import com.example.b_my_friend.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Account(
    @SerializedName("id")
    @Expose
    var id: String = "",

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("email")
    @Expose
    var email: String = "",

    @SerializedName("password")
    @Expose
    var password: String,
    var avatar: Int = R.mipmap.ic_launcher_round){


    override fun toString(): String {
        return "name = $name, email = $email"
    }
}