package com.example.b_my_friend.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.data.model.User
import de.hdodenhof.circleimageview.CircleImageView

class ContactsAdapter(var list: MutableList<User>, private val onClickListener: ItemClickListener)
    :RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.nameView.text = item.name
        holder.infoView.text = item.email
        if (item.avatar != ""){
            val bitmap = BitmapFactory.decodeFile(item.imgPath)
            holder.avatar.setImageBitmap(bitmap)
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(view: View, private val onClickListener: ItemClickListener): RecyclerView.ViewHolder(
        view
    ) {
        val avatar: CircleImageView = view.findViewById(R.id.itemImage)
        val nameView: TextView = view.findViewById(R.id.nameItem)
        val infoView: TextView = view.findViewById(R.id.infoItem)
        init {
            view.setOnClickListener { onClickListener.onItemClick(adapterPosition) }
        }
    }
    interface ItemClickListener{
        fun onItemClick(position: Int)
    }
}