package com.example.b_my_friend.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.databinding.ItemListBinding
import com.example.b_my_friend.model.Chat

class ItemListAdapter(private val myDataset: MutableList<Chat>) :RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemListBinding = DataBindingUtil.inflate( LayoutInflater.from(parent.context)
            ,R.layout.item_list, parent, false)
        return ViewHolder(binding, parent.context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myDataset[position])

    }


    override fun getItemCount() = myDataset.size

    class ViewHolder(private val v: ItemListBinding, val context: Context) : RecyclerView.ViewHolder(v.root) {
        fun bind(chat: Chat) {
            v.nameChat.text = chat.name
            v.infoChat.text = chat.info
            itemView.setOnClickListener {
                Toast.makeText(context, v.nameChat.text, Toast.LENGTH_LONG).show()
            }
        }
    }
}