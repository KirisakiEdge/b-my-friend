package com.example.b_my_friend.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.BlogAdapter
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import kotlinx.android.synthetic.main.fragment_blog.*


class BlogFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        list_blog.addItemDecoration(LayoutMarginDecoration(1, 50)) //scape between items in RecyclerView
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    private fun setAdapter(){
        list_blog.setHasFixedSize(true)
        val manager = LinearLayoutManager(activity)
        list_blog.layoutManager = manager
        val adapter = BlogAdapter()
        list_blog.adapter = adapter
    }
}