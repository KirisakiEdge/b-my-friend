package com.example.b_my_friend.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.GroupsAdapter
import com.example.b_my_friend.data.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_groups.*

class GroupsFragment : Fragment(), GroupsAdapter.ItemClickListener{


    private var list: MutableList<User> = ArrayList()
    private lateinit var adapter: GroupsAdapter
    private val manager = LinearLayoutManager(activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Log.e("TAG", list.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadList()
        setChatAdapter()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_groups, container, false)
    }


    private fun setChatAdapter(){
        list_contacts.setHasFixedSize(true)
        list_contacts.layoutManager = manager
        adapter = GroupsAdapter(list, this@GroupsFragment)
        list_contacts.adapter = adapter
    }


    private fun uploadList(){
        list.add(User(name ="11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name ="11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name ="11111", email = "11111", avatar = R.mipmap.temp_icon_round))
        list.add(User(name ="11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name ="11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name ="11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name ="11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name ="11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
    }


    override fun onItemClick(position: Int, view: View) {
    }
}