package com.example.b_my_friend.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b_my_friend.MainActivity
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.MessageAdapter
import com.example.b_my_friend.data.model.Chat
import com.example.b_my_friend.data.model.User
import kotlinx.android.synthetic.main.fragment_chat.view.*


class ChatFragment : Fragment() {

    var lChat: MutableList<Chat> = ArrayList()
    private lateinit var actionBar: ActionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_chat, container, false)
        actionBar = (requireActivity() as AppCompatActivity).supportActionBar!!
        actionBar.title = ""

        (activity as MainActivity).setActionBarChat(v, requireArguments().getSerializable("user")!! as User,
            requireArguments().getStringArrayList("idList")!!)

        v.list_of_message.setHasFixedSize(true)
        LinearLayoutManager(context).stackFromEnd = true
        v.list_of_message.layoutManager = LinearLayoutManager(context)
        v.list_of_message.adapter = MessageAdapter(lChat)

        return  v
    }

    override fun onDestroyView() {
        super.onDestroyView()

        actionBar = (requireActivity() as AppCompatActivity).supportActionBar!!
        //Log.e("chat", actionBar.toString())
        (activity as MainActivity).setActionBarChat(requireView(),User(avatar = null), ArrayList())


    }
}