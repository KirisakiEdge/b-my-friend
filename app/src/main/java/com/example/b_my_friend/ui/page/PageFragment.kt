package com.example.b_my_friend.ui.page

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.FeedAdapter
import com.example.b_my_friend.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.android.synthetic.main.fragment_photo.*


class PageFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FeedAdapter(requireActivity(), 2)
        feedViewPager.adapter = adapter
        TabLayoutMediator(feedTabsLayout, feedViewPager){
                tab, position ->
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