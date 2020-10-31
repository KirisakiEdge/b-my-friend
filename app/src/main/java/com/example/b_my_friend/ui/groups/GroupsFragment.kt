package com.example.b_my_friend.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.GroupAdapter
import com.example.b_my_friend.data.model.Contact
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_groups.*

class GroupsFragment : Fragment(){


    private var list: MutableList<Contact> = ArrayList()
    private lateinit var adapter: GroupAdapter
    private lateinit var manager: RecyclerView.LayoutManager

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
        list_chat.setHasFixedSize(true)
        manager = LinearLayoutManager(activity)
        list_chat.layoutManager = manager
        adapter = GroupAdapter(list)
        list_chat.adapter = adapter


    }


    private fun uploadList(){
        list.add(Contact(name ="11111", password = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name ="11111", password = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name ="11111", password = "11111", avatar = R.mipmap.temp_icon_round))
        list.add(Contact(name ="11111", password = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name ="11111", password = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name ="11111", password = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name ="11111", password = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name ="11111", password = "11111", avatar = R.mipmap.ic_launcher_round))




    }
}