package com.example.b_my_friend.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class AllFeeds(
    @SerializedName("current_page")
    @Expose
    var currentPage: String = "",

    @SerializedName("data")
    @Expose
    var data: MutableList<Feed> = ArrayList(),

    @SerializedName("first_page_url")
    @Expose
    var firstPageUrl: String = "",

    @SerializedName("from")
    @Expose
    var from: String = "",

    @SerializedName("last_page")
    @Expose
    var lastPage: String = "",

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
    var total: String = ""
)