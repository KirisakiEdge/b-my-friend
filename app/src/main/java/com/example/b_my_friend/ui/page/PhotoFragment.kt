package com.example.b_my_friend.ui.page

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.GridPhotoAdapter
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.AllFeeds
import com.example.b_my_friend.data.model.Feed
import com.example.b_my_friend.networking.NetworkService
import kotlinx.android.synthetic.main.fragment_blog.view.*
import kotlinx.android.synthetic.main.fragment_photo.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class PhotoFragment() : Fragment(), GridPhotoAdapter.ItemLongClickListener {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(PageViewModel::class.java) }
    private val token by lazy {SessionManager(requireActivity()).fetchAuthToken()}
    private var allPhoto: MutableList<Feed> = ArrayList()

    //for search user by id
    private var userId: String = ""
    private var myPage = true
    constructor(userId: String, myPage: Boolean): this(){
        this.userId = userId
        this.myPage = myPage
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.loadingPhoto.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            //if true its blog for myPage, if false its UserPage
            allPhoto = if (myPage) {
                getAllFeeds(token!!).data
            } else {
                getAllFeedsOfSomeUser(token!!, userId).data
            }
            launch(Dispatchers.Main) {
                view.gridPhoto.setHasFixedSize(true)
                view.gridPhoto.adapter = GridPhotoAdapter(allPhoto.asReversed(), this@PhotoFragment)
                view.loadingPhoto.visibility = View.INVISIBLE
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)

    }

    override fun onItemLongClickListener(position: Int, imageRes: Drawable, feed: Feed) {
        val imageByteArray = Base64.decode(feed.img, Base64.DEFAULT) //image in base64  too large for bundle (TransactionTooLargeException) because LiveData
        val imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray!!.size)
        viewModel.setPhotoReview(imageBitmap)
        val bundle = bundleOf(
            "id" to feed.id,
            "desc" to feed.desc,
            "createdAt" to feed.createdAt,
            "actionBarTitle" to (activity as AppCompatActivity?)!!.supportActionBar!!.title)
        //if true its blog for myPage, if false its UserPage
        if (myPage){
            findNavController().navigate(R.id.action_nav_my_page_to_editablePhotoReview, bundle)
        }else {
            findNavController().navigate(R.id.action_UserPageFragment_to_photoReview3, bundle)
        }

    }

    private suspend fun getAllFeeds(token: String): AllFeeds {
        val call = NetworkService().getService().getAllFeeds("Bearer $token", "1")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<AllFeeds> {
                override fun onResponse(call: Call<AllFeeds>, response: Response<AllFeeds>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<AllFeeds>, t: Throwable) {}
            })
        }
    }

    private suspend fun getAllFeedsOfSomeUser(token: String, userId: String): AllFeeds {
        val call = NetworkService().getService().getAllFeedsOfSomeUser("Bearer $token", userId,"1")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<AllFeeds> {
                override fun onResponse(call: Call<AllFeeds>, response: Response<AllFeeds>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<AllFeeds>, t: Throwable) {}
            })
        }
    }

}
