package com.example.b_my_friend.model


class Chat(var name: String = "", var info: String = ""){

    override fun toString(): String {
        return "name = $name, info = $info"
    }
}