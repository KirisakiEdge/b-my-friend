package com.example.b_my_friend.ui.page.myPage

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.FeedAdapter
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.db.AccountDataBase
import com.example.b_my_friend.networking.Count
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.page.PageViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class MyPageFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(PageViewModel::class.java) }
    private val token by lazy { SessionManager(requireActivity()).fetchAuthToken() }
    private val service = NetworkService().getService()



    @SuppressLint("SetTextI18n") //textSize under 18sp
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getCurrentUser().observe(viewLifecycleOwner, {
            Log.e("myPage", it.toString())
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = it.name
            my_page_nick.text = it.name
            GlobalScope.launch {
                launch(Dispatchers.Main) {
                    my_page_avatar.setImageBitmap(BitmapFactory.decodeFile(it.imgPath))
                }
            }
        })
        /*        (activity as AppCompatActivity?)!!.supportActionBar!!.title = viewModel.getCurrentUser().value!!.name
        my_page_nick.text = viewModel.getCurrentUser().value!!.name*/


        GlobalScope.launch(Dispatchers.IO) {
            //val bitmap = BitmapFactory.decodeFile(accountDB.dao().getAccountInfo().imgPath)
            //launch(Dispatchers.Main) { my_page_avatar.setImageBitmap(bitmap) }
            viewModel.setFollow(getAllFollowersCount(), getAllFollowingsCount())
        }


        viewModel.getFollowUser().observe(viewLifecycleOwner, {
            myFollowers.text = "${getString(R.string.followers)} ${it[0].count}"
            myFollowing.text = "${getString(R.string.following)} ${it[1].count}"
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FeedAdapter(requireActivity(), 2,"", true) //myPage - for check if currect page is my(account)
        val refreshActivity =
            requireActivity().findViewById<SwipeRefreshLayout>(R.id.refreshActivity)
        feedViewPager.adapter = adapter
        feedViewPager.isSaveEnabled = false
        TabLayoutMediator(feedTabsLayout, feedViewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Photo"
            } else {
                tab.text = "Blog"
            }
        }.attach()
        feedTabsLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) {
                    view.fab.setImageResource(R.drawable.plus)
                } else {
                    view.fab.setImageResource(android.R.drawable.ic_menu_edit)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        feedTabsLayout.tabGravity = TabLayout.GRAVITY_FILL

        //for refresh icon on top
        my_page_app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            refreshActivity.isEnabled = verticalOffset == 0
        })

        fab.setOnClickListener { view ->
            val tabPosition = feedTabsLayout.selectedTabPosition
            if (tabPosition == 0) {
                view.findNavController().navigate(R.id.action_nav_my_page_to_addNewPhoto)
            } else {
                view.findNavController().navigate(R.id.action_nav_my_page_to_newPostFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    private suspend fun getAllFollowersCount(): Count {
        val call = service.getCountOfFollowers("Bearer $token")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<Count> {
                override fun onResponse(call: Call<Count>, response: Response<Count>) {
                    if (response.body() != null) {
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<Count>, t: Throwable) {}
            })
        }
    }

    private suspend fun getAllFollowingsCount(): Count {
        val call = service.getCountOfFollowing("Bearer $token")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<Count> {
                override fun onResponse(call: Call<Count>, response: Response<Count>) {
                    continuation.resume(response.body()!!)
                }
                override fun onFailure(call: Call<Count>, t: Throwable) {}
            })
        }
    }
}