package com.example.b_my_friend.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.data.model.Feed


class GridPhotoAdapter(private val photos: MutableList<Feed>, private val onClickListener: ItemLongClickListener):
    RecyclerView.Adapter<GridPhotoAdapter.ViewHolder>() {

    lateinit var imageBytes: ByteArray
    private  var image: Bitmap? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_grid_photo, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        imageBytes = Base64.decode(photos[position].img, Base64.DEFAULT)                         //decode base64 to bitmap
        image = imageBytes.size.let { BitmapFactory.decodeByteArray(imageBytes, 0, it) }
        holder.photo.setImageBitmap(image)
        holder.feed = photos[position]

    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class ViewHolder(
        view: View,
        private val onClickListener: ItemLongClickListener
    ): RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.findViewById(R.id.photo)
        var feed: Feed = Feed()
        init {
            view.setOnLongClickListener{
                onClickListener.onItemLongClickListener(adapterPosition, photo.drawable, feed)
            return@setOnLongClickListener true}
        }
    }
    interface ItemLongClickListener{
        fun onItemLongClickListener(position: Int, imageRes: Drawable, feed: Feed)
    }
}