package com.example.b_my_friend.ui.page.usersPage

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.FeedAdapter
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.page.PageViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_page.feedTabsLayout
import kotlinx.android.synthetic.main.fragment_my_page.feedViewPager
import kotlinx.android.synthetic.main.fragment_user_page.*
import kotlinx.android.synthetic.main.fragment_user_page.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPageFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(PageViewModel::class.java) }
    private val service = NetworkService().getService()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userName = arguments?.getString("userName")
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = userName
        user_page_nick.text = userName
        val bitmap = BitmapFactory.decodeFile(arguments?.getString("userAvatar"))
        if (bitmap != null)
            user_page_avatar.setImageBitmap(bitmap)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FeedAdapter(requireActivity(), 2, arguments?.getString("userId")!!, false) //myPage - for check if currect page is my(account)
        val refreshActivity = requireActivity().findViewById<SwipeRefreshLayout>(R.id.refreshActivity)
        feedViewPager.adapter = adapter
        TabLayoutMediator(feedTabsLayout, feedViewPager){ tab, position ->
            if (position == 0)
                tab.text = "Photo"
            else
                tab.text = "Blog"
        }.attach()
        feedTabsLayout.tabGravity = TabLayout.GRAVITY_FILL

        //for refresh icon on top
        user_page_app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                _, verticalOffset -> refreshActivity.isEnabled = verticalOffset == 0
        })
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

