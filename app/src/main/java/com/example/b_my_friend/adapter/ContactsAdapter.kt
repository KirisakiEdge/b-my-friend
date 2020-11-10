package com.example.b_my_friend.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.databinding.ItemListBinding
import com.example.b_my_friend.ui.chat.ChatActivity
import com.example.b_my_friend.data.model.User

class ContactsAdapter(var list: MutableList<User>) :RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemListBinding = DataBindingUtil.inflate( LayoutInflater.from(parent.context)
            ,R.layout.item_list, parent, false)
        return ViewHolder(binding, parent.context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

    }


    override fun getItemCount() = list.size

    class ViewHolder(private val v: ItemListBinding, val context: Context) : RecyclerView.ViewHolder(v.root) {
        private val intent = Intent(context, ChatActivity::class.java)
        fun bind(user: User) {
            v.chatImage.setImageResource(user.avatar)
            v.nameChat.text = user.name
            v.infoChat.text = user.email
            itemView.setOnClickListener {
                intent.putExtra("id", user.id)
                intent.putExtra("Avatar", user.avatar)
                intent.putExtra("NameSurname", user.name)
                context.startActivity(intent)
            }
        }
    }
}