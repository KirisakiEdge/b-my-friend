package com.example.b_my_friend.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_grid_photo.view.*


class GridPhotoAdapter(context: Context) : BaseAdapter() {

    private var imageArrayList = arrayOf(
        com.example.b_my_friend.R.mipmap.temp_icon,  com.example.b_my_friend.R.mipmap.temp_icon,  com.example.b_my_friend.R.mipmap.temp_icon,
        com.example.b_my_friend.R.mipmap.temp_icon,  com.example.b_my_friend.R.mipmap.temp_icon,  com.example.b_my_friend.R.mipmap.temp_icon)

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return imageArrayList.size
    }

    override fun getItem(position: Int): Any {
        return imageArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return imageArrayList[position].toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return if (convertView != null) {
            convertView
        } else {
            val view = inflater.inflate(com.example.b_my_friend.R.layout.item_grid_photo, null)

            view.photo.setImageResource(imageArrayList[position])
            return view
        }
    }

}