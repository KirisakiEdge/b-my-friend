package com.example.b_my_friend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.databinding.ItemListBinding
import com.example.b_my_friend.model.Chat

class ChatListAdapter(private val myDataset: MutableList<Chat>) :RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemListBinding = DataBindingUtil.inflate( LayoutInflater.from(parent.context)
            ,R.layout.item_list, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myDataset[position])

    }


    override fun getItemCount() = myDataset.size

    class ViewHolder(v: ItemListBinding) : RecyclerView.ViewHolder(v.root) {
        private val nameChat: TextView = itemView.findViewById(R.id.nameChat)
        private val infoChat: TextView = itemView.findViewById(R.id.infoChat)

        fun bind(chat: Chat){
            nameChat.text = chat.name
            infoChat.text = chat.info
        }

    }
}