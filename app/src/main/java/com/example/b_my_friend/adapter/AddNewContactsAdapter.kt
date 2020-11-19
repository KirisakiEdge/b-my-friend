package com.example.b_my_friend.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.b_my_friend.R
import com.example.b_my_friend.data.model.User
import de.hdodenhof.circleimageview.CircleImageView


class AddNewContactsAdapter(private val values: MutableList<User>, private val onClickListener: ItemClickListener)
    : RecyclerView.Adapter<AddNewContactsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if (item.name != "") {
            holder.nameView.text = item.name
            holder.nameView.background = null
            holder.infoView.text = item.email
            holder.infoView.background = null
            holder.avatar.setImageResource(item.avatar)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View, private val onClickListener: ItemClickListener) : RecyclerView.ViewHolder(view) {
        val avatar: CircleImageView = view.findViewById(R.id.itemImage)
        val nameView: TextView = view.findViewById(R.id.nameItem)
        val infoView: TextView = view.findViewById(R.id.infoItem)
        init {
            view.setOnClickListener { onClickListener.onItemClick(adapterPosition, view) }
        }
    }
    interface ItemClickListener{
        fun onItemClick(position: Int, view: View)
    }
}
