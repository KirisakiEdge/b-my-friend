package com.example.b_my_friend.ui.page.usersPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.b_my_friend.R
import com.example.b_my_friend.ui.page.PageViewModel
import kotlinx.android.synthetic.main.review_item_photo.view.*


class PhotoReview: DialogFragment() {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(PageViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.photoReview.setImageBitmap(viewModel.getPhotoReview().value)
        view.desc.text = arguments?.getString("desc")
        view.createdAt.text = arguments?.getString("createdAt")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.review_item_photo, container, false)
    }

    override fun onDestroyView() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = arguments?.getString("actionBarTitle")
        super.onDestroyView()
    }
}