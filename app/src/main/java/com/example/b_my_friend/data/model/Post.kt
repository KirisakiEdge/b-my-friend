package com.example.b_my_friend.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: String = "",

    @SerializedName("user_id")
    @Expose
    @ColumnInfo(name = "user_id")
    var userId: String = "",

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String = "",

    @SerializedName("body")
    @Expose
    @ColumnInfo(name = "body")
    var body: String? = null,

    @SerializedName("created_at")
    @Expose
    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,

    @SerializedName("updated_at")
    @Expose
    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = null
)
