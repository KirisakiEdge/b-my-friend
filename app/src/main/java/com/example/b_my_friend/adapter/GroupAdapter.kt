package com.example.b_my_friend.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.databinding.ItemListBinding
import com.example.b_my_friend.ui.chat.ChatActivity
import com.example.b_my_friend.data.model.User

class GroupAdapter(private val myDataset: MutableList<User>) :RecyclerView.Adapter<GroupAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemListBinding = DataBindingUtil.inflate( LayoutInflater.from(parent.context),
            R.layout.item_list, parent, false)
        return ViewHolder(binding, parent.context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myDataset[position])

    }


    override fun getItemCount() = myDataset.size

    class ViewHolder(private val v: ItemListBinding, val context: Context) : RecyclerView.ViewHolder(v.root) {
            private val intent = Intent(context, ChatActivity::class.java)
        fun bind(user: User) {
            v.chatImage.setImageResource(user.avatar)
            v.nameChat.text = user.name
            v.infoChat.text = user.email
            itemView.setOnClickListener {
                Toast.makeText(context, user.name, Toast.LENGTH_LONG).show()

            }
        }
    }
}