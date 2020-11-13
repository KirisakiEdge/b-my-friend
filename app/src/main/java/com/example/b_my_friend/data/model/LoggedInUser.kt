package com.example.b_my_friend.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoggedInUser(
    @SerializedName("access_token")
    @Expose
    var accessToken: String = "",

    @SerializedName("user")
    @Expose
    var user: User,

    @SerializedName("token_type")
    @Expose
    var tokenType: String = ""){

}