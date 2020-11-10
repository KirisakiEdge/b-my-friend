package com.example.b_my_friend.ui.contacts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.ContactsAdapter
import com.example.b_my_friend.data.model.Account
import com.example.b_my_friend.data.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_contacts.*
import java.lang.Exception

class ContactsFragment : Fragment() {

    var list: MutableList<User> = ArrayList()
    private lateinit var adapter: ContactsAdapter

    private var manager = LinearLayoutManager(activity)
    lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // Log.e("TAG", list.toString())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        val listChat = view.findViewById<RecyclerView>(R.id.list_contacts)
        try { //Because logout dialog go to contacts for background when manager is older
            uploadList()
            setChatAdapter(listChat)
        }catch (e: Exception){
            Log.e("TAG", e.toString())
            manager = LinearLayoutManager(activity)
            listChat.layoutManager = manager
            setChatAdapter(listChat)
        }

        return view
    }


    private fun setChatAdapter(listView: RecyclerView){

        //temp
        adapter = ContactsAdapter(list, )
        listView.layoutManager = manager
        listView.adapter = adapter

       // Log.e("TAG", "$list")
        //list_chat.setHasFixedSize(true)

       /* Thread{
            reference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    snapshot.children.forEach {
                         contact = it.getValue(Contact::class.java)!!
                        if (contact.id != firebaseUser.uid)
                        list.add(contact)
                    }
                        adapter = ContactsAdapter(list)
                        list_chat.layoutManager = manager
                        list_chat.adapter = adapter

                }
                override fun onCancelled(error: DatabaseError) {
                }

            })
        }.start()*/


    }


    private fun uploadList(){
        list.clear() // when star try catch for logout need clear list
        list.add(User(name = "11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name = "11222221", email = "133311", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name = "1122221", email = "114444411", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name = "112222211", email = "5555555", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name = "11111", email = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name = "11222221", email = "133311", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name = "1122221", email = "114444411", avatar = R.mipmap.ic_launcher_round))
        list.add(User(name = "112222211", email = "5555555", avatar = R.mipmap.ic_launcher_round))
    }

}