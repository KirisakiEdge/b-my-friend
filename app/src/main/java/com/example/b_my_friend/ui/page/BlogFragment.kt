package com.example.b_my_friend.ui.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.BlogAdapter
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.AllPosts
import com.example.b_my_friend.networking.NetworkService
import kotlinx.android.synthetic.main.fragment_blog.*
import kotlinx.android.synthetic.main.fragment_blog.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class BlogFragment() : Fragment(),  BlogAdapter.ItemLongClickListener {
    private val token by lazy { SessionManager(requireActivity()).fetchAuthToken() }
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(PageViewModel::class.java) }


    //for search user by id
    private var userId: String = ""
    private var myPage = true
    constructor(userId: String, myPage: Boolean): this(){
        this.userId = userId
        this.myPage = myPage
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.loadingBlog.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            //if true its blog for myPage, if false its UserPage
            val postsList = if (myPage) {
                getAllPosts(token!!).data
            } else {
                getAllPostsOfSomeUser(token!!, userId).data
            }
            launch(Dispatchers.Main) {
                view.list_blog.setHasFixedSize(true)
                view.list_blog.adapter = BlogAdapter(postsList.asReversed(),
                    this@BlogFragment) //reversed for new post at first
                loadingBlog.visibility = View.INVISIBLE //after loading blog invisible bar
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    private suspend fun getAllPosts(token: String): AllPosts {
        val call = NetworkService().getService().getAllPosts("Bearer $token", "1")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<AllPosts> {
                override fun onResponse(call: Call<AllPosts>, response: Response<AllPosts>) {
                    if (response.isSuccessful){
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<AllPosts>, t: Throwable) {}
            })
        }
    }

    override fun onItemLongClickListener(position: Int, blogId: String, title: String, text: String) {
        //if true its blog for myPage, if false its UserPage
        if (myPage){
            val bundle = bundleOf("blogId" to blogId, "title" to title, "text" to text,
                "actionBarTitle" to (activity as AppCompatActivity?)!!.supportActionBar!!.title)  // for set title when dialog destroy
            findNavController().navigate(R.id.action_nav_my_page_to_postEditFragment, bundle)
        }
    }

    private suspend fun getAllPostsOfSomeUser(token: String, userId: String): AllPosts {
        val call = NetworkService().getService().getAllPostsOfSomeUser("Bearer $token", userId,"1")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<AllPosts> {
                override fun onResponse(call: Call<AllPosts>, response: Response<AllPosts>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<AllPosts>, t: Throwable) {}
            })
        }
    }

}