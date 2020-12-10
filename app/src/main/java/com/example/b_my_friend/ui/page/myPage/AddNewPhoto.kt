package com.example.b_my_friend.ui.page.myPage

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import kotlinx.android.synthetic.main.fragment_add_new_photo.*
import kotlinx.android.synthetic.main.fragment_add_new_photo.view.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.review_item_photo.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class AddNewPhoto : DialogFragment(){

    private val REQUEST_IMAGE_CAPTURE = 1
    private val RESULT_LOAD_IMG = 1
    private var base64Image: String = ""
    private val token by lazy { SessionManager(requireActivity()).fetchAuthToken() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.addPhoto.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
        }
        view.addPhotoWaterMark.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
        }

        view.sendPhoto.setOnClickListener {
            if (base64Image != "" && view.newDesc.text.toString() != ""){
                sendImage(token!!, base64Image, view.newDesc.text.toString())
                requireActivity().recreate()
                findNavController().popBackStack()
            }else{
                Toast.makeText(requireContext(), "Photos and description are required", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_photo, container, false)
    }


    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri: Uri? = data!!.data
                addPhoto.setImageURI(imageUri)
                val selectedImage = MediaStore.Images.Media.getBitmap(
                    requireActivity().applicationContext.contentResolver, imageUri)
                // my_page_avatar.setImageBitmap(selectedImage)
                val stream = ByteArrayOutputStream()
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, stream)
                val image = stream.toByteArray()
                if (image.size < 2000000) {   //limit to 2mb
                    GlobalScope.launch(Dispatchers.Main) {
                        base64Image = Base64.encodeToString(image, Base64.DEFAULT)
                        base64Image = base64Image.replace("\\s".toRegex(), "")
                    }
                }else{
                    Toast.makeText(requireContext(), "Image must be until 2 mb", Toast.LENGTH_LONG).show()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(requireContext(), "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendImage(token: String, image: String, desc: String) {
        val call = NetworkService().getService().createFeed("Bearer $token", desc, image)
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