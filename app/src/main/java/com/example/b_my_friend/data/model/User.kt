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
    var emailVerifiedAt: String? = null,

    @SerializedName("email_verification_token")
    @Expose
    var emailVerificationToken: String? = null,

    var avatar: Int = R.drawable.temp){


    override fun toString(): String {
        return "name = $name, email = $email"
    }
}