package com.example.b_my_friend.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.data.model.Post

class BlogAdapter (private val postsList: MutableList<Post>, private val onClickListener: ItemLongClickListener) :
    RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
        return ViewHolder(view, onClickListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = postsList[position]
        holder.title.text = item.title
        holder.text.text = item.body
        if (item.createdAt == item.updatedAt)
            holder.timeCreated.text = item.createdAt
        else
            holder.timeCreated.text = "(edited)${item.updatedAt}"
        holder.blogId = item.id

    }


    override fun getItemCount() = postsList.size

    inner class ViewHolder(view: View, private val onClickListener: ItemLongClickListener) :
        RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.postTitle)
        val text: TextView = view.findViewById(R.id.postText)
        val timeCreated: TextView = view.findViewById(R.id.timeCreated)
        var blogId: String = ""
        init {
            view.setOnLongClickListener { onClickListener.onItemLongClickListener(adapterPosition,
                blogId, title.text.toString(), text.text.toString())
                return@setOnLongClickListener true}
        }
    }
    interface ItemLongClickListener{
        fun onItemLongClickListener(position: Int, blogId: String, title: String, text: String)
    }
}