package com.example.b_my_friend.adapter

import android.content.Context
import androidx.annotation.NonNull
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.b_my_friend.ui.page.BlogFragment
import com.example.b_my_friend.ui.page.PhotoFragment


@Suppress("DEPRECATION")
    class FeedAdapter(fa: FragmentActivity, private var totalTabs: Int) : FragmentStateAdapter(fa) {
    // this is for fragment tabs
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PhotoFragment()
            }
            1 -> {
                BlogFragment()
            }
            else -> PhotoFragment()
        }
    }
    override fun getItemCount(): Int {
        return totalTabs
    }
}