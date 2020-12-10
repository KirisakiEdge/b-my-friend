package com.example.b_my_friend.ui.page.myPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.page.PageViewModel
import kotlinx.android.synthetic.main.fragment_editable_photo_review.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditablePhotoReview : DialogFragment() {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(PageViewModel::class.java) }
    private val token by lazy { SessionManager(requireActivity()).fetchAuthToken() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.myPhotoReview.setImageBitmap(viewModel.getPhotoReview().value)
        view.myDesc.text = arguments?.getString("desc")
        view.myCreatedAt.text = arguments?.getString("createdAt")



        view.deletePhoto.setOnClickListener {
            deletePhoto(arguments?.getString("id")!!)
            findNavController().popBackStack()
            requireActivity().recreate()
        }



        val desc = EditText(requireContext())
        desc.setText(view.myDesc.text)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Update Description")
        builder.setView(desc)
        builder.setPositiveButton("Ok") { _, _ ->
            if (desc.text.toString() != "") {
                updateFeed(token!!, arguments?.getString("id")!!, desc.text.toString())
                findNavController().popBackStack()
                requireActivity().recreate()
            } else {
                Toast.makeText(requireContext(), "the field must be filled", Toast.LENGTH_LONG).show()
            }
        }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()

        view.updateFeed.setOnClickListener {
            dialog.show()
        }
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editable_photo_review, container, false)
    }

    private fun updateFeed(token: String, id: String, desc: String){
        val call = NetworkService().getService().updateFeed("Bearer $token", id, desc)
        call.enqueue(object : Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {}
            override fun onFailure(call: Call<Message>, t: Throwable) {}
        })
    }

    private fun deletePhoto(id: String){
        val call = NetworkService().getService().deleteFeed("Bearer $token", id)
        call.enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {}
            override fun onFailure(call: Call<Message>, t: Throwable) {}
        })
    }

override fun onDestroyView() {
    (activity as AppCompatActivity?)!!.supportActionBar!!.title = arguments?.getString("actionBarTitle")
    super.onDestroyView()
}
}