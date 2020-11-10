package com.example.b_my_friend.ui.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.FeedAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_page.*


class PageFragment : Fragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel= ViewModelProviders.of(requireActivity()).get(PageViewModel::class.java)
        viewModel.getCurrentUser().observe(viewLifecycleOwner, Observer {
            //Log.e("page", it.toString())
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = it.name
        })

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FeedAdapter(requireActivity(), 2)
        feedViewPager.adapter = adapter
        TabLayoutMediator(feedTabsLayout, feedViewPager){ tab, position ->
            if (position == 0)
                tab.text = "Photo"
            else
                tab.text = "Blog"
        }.attach()
        feedTabsLayout.tabGravity = TabLayout.GRAVITY_FILL

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }



}

