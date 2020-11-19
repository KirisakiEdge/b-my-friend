package com.example.b_my_friend.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.b_my_friend.R
import kotlinx.android.synthetic.main.item_grid_photo.view.*
import kotlinx.android.synthetic.main.review_item_photo.view.*


class GridPhotoAdapter(val context: FragmentActivity) : BaseAdapter() {

    private var imageArrayList = arrayOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground
    )

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

        //view.photo.setImageResource(imageArrayList[position])
        view.photo.setImageResource(R.drawable.temp)

        view.setOnLongClickListener {
            //val pr = PhotoReview(imageArrayList[position])
            val pr = PhotoReview(R.drawable.temp)
            pr.show(context.supportFragmentManager, " Photo Review")

            return@setOnLongClickListener true
        }



    /*    view.setOnClickListener {
            Toast.makeText(context, "CLICK${position + 1}", Toast.LENGTH_LONG).show()
        }*/


        return view
    }

    class PhotoReview(private val imageResource: Int): DialogFragment() {

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            view.photoReview.setImageResource(imageResource)
        }
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.review_item_photo, container, false)
        }
    }

}