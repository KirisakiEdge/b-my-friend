package com.example.b_my_friend.model

import android.graphics.Bitmap

class Chat(var name: String = "", var info: String = ""){
    //var bitmap: Bitmap? = null

    override fun toString(): String {
        return "name = $name, info = $info"
    }
}