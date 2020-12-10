package com.example.b_my_friend.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Feed (
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: String = "",

    @SerializedName("user_id")
    @Expose
    @ColumnInfo(name = "user_id")
    var userId: String = "",

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    var desc: String = "",

    @SerializedName("created_at")
    @Expose
    @ColumnInfo(name = "created_at")
    var createdAt: String = "",

    @SerializedName("updated_at")
    @Expose
    @ColumnInfo(name = "updated_at")
    var updatedAt: String = "",

    @SerializedName("img")
    @Expose
    @ColumnInfo(name = "img")
    var img: String = "")