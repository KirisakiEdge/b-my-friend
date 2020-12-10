package com.example.b_my_friend.ui.page.myPage

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import kotlinx.android.synthetic.main.fragment_editable_photo_review.view.*
import kotlinx.android.synthetic.main.fragment_post_edit.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostEditFragment : DialogFragment() {

    private val token by lazy { SessionManager(requireActivity()).fetchAuthToken() }

    override fun onStart() {
        super.onStart()
        //for  custom dialog params
        dialog!!.window!!.setGravity(Gravity.CENTER)
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_post_edit, container, false)
        v.updatePost.setOnClickListener {
            val bundle = bundleOf(
                "blogId" to requireArguments().getString("blogId"),
                "title" to requireArguments().getString("title"),
                "text" to requireArguments().getString("text"))
            findNavController().navigate(R.id.action_postEditFragment_to_new_post, bundle)
        }
        v.deletePost.setOnClickListener {
            deletePost(requireArguments().getString("blogId")!!)
            findNavController().popBackStack()
            requireActivity().recreate()
        }
        return v
    }


    private fun deletePost(id: String){
        val call = NetworkService().getService().deletePost("Bearer $token", id)
        call.enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {}
            override fun onFailure(call: Call<Message>, t: Throwable) {}
        })
    }
    //when view is destroy, actionbar is empty, because we override onDestroyView for se title
    override fun onDestroyView() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = arguments?.getString("actionBarTitle")
        super.onDestroyView()
    }

}