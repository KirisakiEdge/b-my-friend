package com.example.b_my_friend.ui.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.FeedAdapter
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_user_page.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserPageFragment : Fragment() {

    private val service = NetworkService().getService()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userName = arguments?.getString("userName")
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = userName
        pageNick.background = null
        pageNick.text = userName
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
        val v = inflater.inflate(R.layout.fragment_user_page, container, false)
        val userId = arguments?.getString("userId")

        if (!requireArguments().getStringArrayList("idList")!!.contains(requireArguments().getString("userId")!!)){
            v.follow.text = resources.getString(R.string.follow)
        }else{
            v.follow.text = resources.getString(R.string.unFollow)
        }

        //Log.e("userpage", requireArguments().getStringArrayList("idList").toString())
        v.follow.setOnClickListener {
            if (v.follow.text == resources.getString(R.string.follow)) {
                val call = service.followToSomeUser(
                    "Bearer ${SessionManager(requireContext()).fetchAuthToken()}", userId!!)
                call.clone().enqueue(object: Callback<Message>{
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        v.follow.text = resources.getString(R.string.unFollow)
                    }
                    override fun onFailure(call: Call<Message>, t: Throwable) {}
                })
            }
            else{
                val call = service.unFollowToSomeUser(
                    "Bearer ${SessionManager(requireContext()).fetchAuthToken()}", userId!!)
                call.clone().enqueue(object: Callback<Message>{
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            v.follow.text = resources.getString(R.string.follow)
                    }
                    override fun onFailure(call: Call<Message>, t: Throwable) {}
                })
            }
        }
        return v
    }
}

