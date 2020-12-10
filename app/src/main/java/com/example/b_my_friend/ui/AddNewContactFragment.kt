package com.example.b_my_friend.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.AddNewContactsAdapter
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.AllUser
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.networking.NetworkService
import kotlinx.android.synthetic.main.fragment_add_new_contact_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AddNewContactFragment : DialogFragment(), AddNewContactsAdapter.ItemClickListener {

    private var list: MutableList<User> = ArrayList()
    private var searchableList: MutableList<User> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_contact_list, container, false)
        val listNewContacts = view.findViewById<RecyclerView>(R.id.newContactsList)
        val idFollowingList = requireArguments().getStringArrayList("idList")
        // Set default adapter
        view.loadingNewContacts.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            getAllUser().forEach { //filter already following user
                if (idFollowingList != null){
                    if (!idFollowingList.contains(it.id)){
                        list.add(it)
                    }
                }
            }
            view.loadingNewContacts.visibility = View.INVISIBLE
            listNewContacts.layoutManager = LinearLayoutManager(context)
            listNewContacts.adapter = AddNewContactsAdapter(list, this@AddNewContactFragment)
        }

        view.new_contact_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                searchableList.clear()
                if (newText!!.isNotEmpty()) {
                    list.forEach {
                        if (it.name.contains(newText) || it.email.contains(newText)) {
                            searchableList.add(it)
                        }
                    }
                    view.loadingNewContacts.visibility = View.INVISIBLE
                    listNewContacts.adapter =
                        AddNewContactsAdapter(searchableList, this@AddNewContactFragment)
                }else {
                    view.loadingNewContacts.visibility = View.INVISIBLE
                    listNewContacts.adapter = AddNewContactsAdapter(list, this@AddNewContactFragment)
                }
                return true
            }
        })
        return view
    }


    override fun onItemClick(position: Int, view: View) {
        val userId = list[position].id
        val userName = list[position].name
        val bundle = bundleOf("userName" to userName, "userId" to userId, "idList" to requireArguments().getStringArrayList("idList"))
        //for user which already following (button follow)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.action_addNewContactFragment_to_userPageFragment, bundle)
    }

    private suspend fun getAllUser(): MutableList<User> {
        val call = NetworkService().getService()
            .getAllUsers("Bearer ${SessionManager(requireContext()).fetchAuthToken()}", "1")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<AllUser> {
                override fun onResponse(call: Call<AllUser>, response: Response<AllUser>) {
                    if (response.isSuccessful) {
                        //Log.e("newContacts", response.body()!!.data.toString())
                        continuation.resume(response.body()!!.data)
                    }else{
                        //Log.e("newContacts", response.message())
                        //Toast.makeText(requireContext(), "Please connect to Internet", Toast.LENGTH_LONG).show()
                        continuation.resume(ArrayList())
                    }
                }

                override fun onFailure(call: Call<AllUser>, t: Throwable) {
                    Toast.makeText(requireContext(), "Please connect to internet", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}