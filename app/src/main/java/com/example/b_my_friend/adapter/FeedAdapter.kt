package com.example.b_my_friend.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.b_my_friend.ui.page.BlogFragment
import com.example.b_my_friend.ui.page.PhotoFragment


class FeedAdapter(fa: FragmentActivity, private var totalTabs: Int, private val userId: String, private val myPage: Boolean) :
    FragmentStateAdapter(fa) {
    // this is for fragment tabs
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotoFragment(userId, myPage)
            1 -> BlogFragment(userId, myPage)
            else -> PhotoFragment(userId, myPage)
        }
    }
    override fun getItemCount(): Int {
        return totalTabs
    }

}