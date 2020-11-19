package com.example.b_my_friend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.data.model.Chat

class MessageAdapter(private var list: MutableList<Chat>) :RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private val MSG_TYPE_LEFT = 0
    private val MSG_TYPE_RIGHT = 1
    private val MSG_TYPE_WITHOUT_AVATAR = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            MSG_TYPE_RIGHT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_right_message, parent, false)
                return ViewHolder(view)
            }
            MSG_TYPE_LEFT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_left_message, parent, false)
                return ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_without_avatar, parent, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.text.text = item.message
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        /* user = FirebaseAuth.getInstance().currentUser!!
         return if (list[position].sender == user.uid){
             MSG_TYPE_RIGHT
         }else {
             if (position != 0 && position != itemCount-1) {
                 if (list[position].receiver == list[position + 1].receiver) {
                     MSG_TYPE_WITHOUT_AVATAR
                 } else {
                     MSG_TYPE_LEFT
                 }
             }else
                 MSG_TYPE_LEFT
         }*/
        return MSG_TYPE_LEFT //temp
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.textMessage)
    }
}