package com.example.b_my_friend.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
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

    lateinit var imageBytes: ByteArray
    private  var image: Bitmap? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name
        holder.infoView.text = item.email
        if (item.avatar != ""){
            imageBytes = Base64.decode(item.avatar, Base64.DEFAULT)                         //decode base64 to bitmap
            image = imageBytes.size.let { BitmapFactory.decodeByteArray(imageBytes, 0, it) }
            holder.avatar.setImageBitmap(image)
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
