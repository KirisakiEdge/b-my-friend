package com.example.b_my_friend.ui.chats

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.ChatListAdapter
import com.example.b_my_friend.model.Chat
import kotlinx.android.synthetic.main.fragment_chats.*

class ChatsFragment : Fragment() {

    private var list: MutableList<Chat> = ArrayList()
    private lateinit var adapter: ChatListAdapter
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadList()
       // Log.e("TAG", list.toString())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadList()
        setChatAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }


    private fun setChatAdapter(){
        list_chat.setHasFixedSize(true)
        manager = LinearLayoutManager(activity)
        Log.e("TAG", manager.toString())
        list_chat.layoutManager = manager
        adapter = ChatListAdapter(list)
        list_chat.adapter = adapter


    }


    private fun uploadList(){
        list.add(Chat("11111", "11111"))
        list.add(Chat("11222221", "133311"))
        list.add(Chat("1122221", "114444411"))
        list.add(Chat("112222211", "5555555"))
    }
}