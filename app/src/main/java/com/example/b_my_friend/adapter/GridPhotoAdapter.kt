package com.example.b_my_friend.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.b_my_friend.R
import kotlinx.android.synthetic.main.item_grid_photo.view.*


class GridPhotoAdapter(val context: Context) : BaseAdapter() {

    private var imageArrayList = arrayOf(
        R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground)

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

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.item_grid_photo, null)

        view.setOnClickListener {
            Toast.makeText(context, "CLICK${position + 1}", Toast.LENGTH_LONG).show()
        }

        view.photo.setImageResource(imageArrayList[position])


        return view
    }

}