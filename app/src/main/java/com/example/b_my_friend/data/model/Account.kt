package com.example.b_my_friend.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.b_my_friend.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Account(
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: String = "",

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    var name: String = "",

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    var email: String = "",

    @SerializedName("password")
    @Expose
    @ColumnInfo(name = "password")
    var password: String,

    @SerializedName("img")
    @Expose
    @ColumnInfo(name = "img")
    var avatar: String = ""){


    override fun toString(): String {
        return "name = $name, email = $email"
    }
}