package com.example.b_my_friend.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllFollow (
    @SerializedName("current_page")
    @Expose
    var currentPage: String = "",

    @SerializedName("data")
    @Expose
    var data: MutableList<User>,

    @SerializedName("first_page_url")
    @Expose
    var firstPageUrl: String = "",

    @SerializedName("from")
    @Expose
    var from: String = "",

    @SerializedName("last_page")
    @Expose
    var last_page: String = "",

    @SerializedName("last_page_url")
    @Expose
    var lastPageUrl: String = "",

    @SerializedName("next_page_url")
    @Expose
    var nextPageUrl: String = "",

    @SerializedName("path")
    @Expose
    var path: String = "",

    @SerializedName("per_page")
    @Expose
    var per_page: String = "",

    @SerializedName("prev_page_url")
    @Expose
    var prevPageUrl: String = "",

    @SerializedName("to")
    @Expose
    var to: String = "",

    @SerializedName("total")
    @Expose
    var total: String = "",
)

/*
data class Followers(
    @SerializedName("id")
    @Expose
    var id: String = "",

    @SerializedName("follower_id")
    @Expose
    var followerId: String = "",

    @SerializedName("following_id")
    @Expose
    var followingId: String = "",

    @SerializedName("created_at")
    @Expose
    var createdAt: String = "",

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = "",

    @SerializedName("follower")
    @Expose
    var follower: User = User(),

    @SerializedName("following")
    @Expose
    var following: User = User(),
)*/
