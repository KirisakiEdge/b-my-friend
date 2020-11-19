package com.example.b_my_friend.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.ContactsAdapter
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.AllUser
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.networking.Count
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.page.PageViewModel
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContactsFragment: Fragment(), ContactsAdapter.ItemClickListener{

    var list: MutableList<User> = ArrayList()
    private var idFollowingList: MutableList<String> = ArrayList()
    private val service = NetworkService().getService()
    private val viewModel by lazy { ViewModelProviders.of(requireActivity()).get(PageViewModel::class.java) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val listContacts = requireView().findViewById<RecyclerView>(R.id.list_contacts)
        val manager = LinearLayoutManager(activity)
        listContacts.layoutManager = manager


        viewModel.getMyFollowings().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val adapter = ContactsAdapter(it, this@ContactsFragment)
                listContacts.adapter = adapter
            }
        })

        GlobalScope.launch(Dispatchers.Main) {
            list = getAllFollowing()
            viewModel.setMyFollowings(list)
            idFollowingList.clear()
            list.forEach {
                idFollowingList.add(it.id)
            }
            val adapter = ContactsAdapter(list, this@ContactsFragment)
            listContacts.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fab.setOnClickListener { view ->
            val bundle = bundleOf("idList" to idFollowingList)
            view.findNavController().navigate(R.id.action_nav_contacts_to_addNewContactFragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_contacts, container, false)
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.setFollow(getAllFollowersCount(), getAllFollowingsCount())
        }
        return v
    }

    override fun onItemClick(position: Int, view: View) {
        val bundle = bundleOf(
            "email" to list[position].email,
            "Avatar" to list[position].avatar,
            "NameSurname" to list[position].name)
        view.findNavController().navigate(R.id.action_nav_contacts_to_chatFragment, bundle)
    }


    private suspend fun getAllFollowing(): MutableList<User> {
        val call = service
            .getAllFollowing("Bearer ${SessionManager(requireContext()).fetchAuthToken()}", "1")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<AllUser> {
                override fun onResponse(call: Call<AllUser>, response: Response<AllUser>) {
                    if (response.isSuccessful) {
                        //Log.e("contacts", response.body()!!.data.toString())
                        continuation.resume(response.body()!!.data)
                    }else{
                        //Log.e("contacts", response.message())
                        uploadList()
                        continuation.resume(list)
                    }
                }
                override fun onFailure(call: Call<AllUser>, t: Throwable) {
                    uploadList()
                    continuation.resume(list)
                }
            })
        }
    }
    private suspend fun getAllFollowersCount(): Count {
        val call = service.getCountOfFollowers(
            "Bearer ${SessionManager(requireContext()).fetchAuthToken()}")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<Count> {
                override fun onResponse(call: Call<Count>, response: Response<Count>) {
                    continuation.resume(response.body()!!)
                }
                override fun onFailure(call: Call<Count>, t: Throwable) {}
            })
        }
    }

    private suspend fun getAllFollowingsCount(): Count {
        val call = service.getCountOfFollowing(
            "Bearer ${SessionManager(requireContext()).fetchAuthToken()}")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<Count> {
                override fun onResponse(call: Call<Count>, response: Response<Count>) {
                    continuation.resume(response.body()!!)
                }
                override fun onFailure(call: Call<Count>, t: Throwable) {}
            })
        }
    }

    private fun uploadList(){
        list.add(User(name ="", email = "", avatar = 0))
        list.add(User(name ="", email = "", avatar = 0))
        list.add(User(name ="", email = "", avatar = 0))
        list.add(User(name ="", email = "", avatar = 0))
        list.add(User(name ="", email = "", avatar = 0))
        list.add(User(name ="", email = "", avatar = 0))
        list.add(User(name ="", email = "", avatar = 0))
        list.add(User(name ="", email = "", avatar = 0))
    }
}