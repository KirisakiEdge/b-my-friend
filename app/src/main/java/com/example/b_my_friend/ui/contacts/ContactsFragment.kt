package com.example.b_my_friend.ui.contacts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.ContactsAdapter
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.AllUser
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.db.UserDataBase
import com.example.b_my_friend.networking.NetworkService
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ContactsFragment: Fragment(), ContactsAdapter.ItemClickListener{

    var list: MutableList<User> = ArrayList()
    private var idFollowingList: MutableList<String> = ArrayList()
    private val service = NetworkService().getService()
    private val token by lazy { SessionManager(requireActivity()).fetchAuthToken() }
    private val userDB by lazy { Room.databaseBuilder(requireContext(), UserDataBase::class.java, "user.db"
    ).build() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val listContacts = requireView().findViewById<RecyclerView>(R.id.list_contacts)

        GlobalScope.launch(Dispatchers.Main) {
            val cacheDir = requireContext().cacheDir
            val following = userDB.dao().following()
            val adapterDB = ContactsAdapter(following, this@ContactsFragment)
            listContacts.adapter = adapterDB

            //update list when internet connect   //if quick click in following db does not have time rewrites
            list = getAllFollowing()
            if (list.isNotEmpty()) {
                launch(Dispatchers.IO) {
                    idFollowingList.clear()
                    userDB.dao().clearUsers()
                    list.forEach {
                        idFollowingList.add(it.id)
                        val file = File(cacheDir.absolutePath, "${it.id}.jpg")
                        file.writeBase64toBitmap(it.avatar, Bitmap.CompressFormat.JPEG, 85)
                        it.imgPath = file.absolutePath
                        userDB.dao().saveNewUser(it)
                    }
                    launch(Dispatchers.Main) {
                        val adapterNet = ContactsAdapter(list, this@ContactsFragment)
                        listContacts.adapter = adapterNet
                    }

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_contacts, container, false)
        //for myPage counter
        v.fab.setOnClickListener { view ->
            val bundle = bundleOf("idList" to idFollowingList)//for user which already following (button follow)
            findNavController().navigate(R.id.action_nav_contacts_to_addNewContactFragment, bundle)
        }
        return v
    }

    override fun onItemClick(position: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val bundle = bundleOf(
                "user" to userDB.dao().following()[position],
                "idList" to idFollowingList
            )
            findNavController().navigate(R.id.action_nav_contacts_to_chatFragment, bundle)
        }
    }

    private fun File.writeBase64toBitmap(avatar: String?, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            val imageByteArray = Base64.decode(avatar, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            if (avatar != "")
                bitmap.compress(format, quality, out)
                out.flush()
        }
    }

    private suspend fun getAllFollowing(): MutableList<User> {
        val call = service
            .getAllFollowing("Bearer $token", "1")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<AllUser> {
                override fun onResponse(call: Call<AllUser>, response: Response<AllUser>) {
                    if (response.isSuccessful) {
                        //Log.e("contacts", response.body()!!.data.toString())
                        continuation.resume(response.body()!!.data)
                    } else {
                        //Log.e("contacts", response.message())
                        continuation.resume(list)
                    }
                }

                override fun onFailure(call: Call<AllUser>, t: Throwable) {
                    continuation.resume(list)
                }
            })
        }
    }
}