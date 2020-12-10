package com.example.b_my_friend.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class User(
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

    @SerializedName("email_verified_at")
    @Expose
    @ColumnInfo(name = "email_verified_at")
    var emailVerifiedAt: String? = null,

    @SerializedName("email_verification_token")
    @Expose
    @ColumnInfo(name = "email_verification_token")
    var emailVerificationToken: String? = null,

    @SerializedName("img")
    @Expose
    @ColumnInfo(name = "img")
    var avatar: String? = "",

    @ColumnInfo(name = "imgPath")  //path to image in cache
    var imgPath: String? = "",
): Serializable{   // for send in bundle

    override fun toString(): String {
        return "name = $name, email = $email, imgPath = $imgPath"
    }
}

