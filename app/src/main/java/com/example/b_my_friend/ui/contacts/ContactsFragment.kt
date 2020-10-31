package com.example.b_my_friend.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.ContactsAdapter
import com.example.b_my_friend.data.model.Contact
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : Fragment() {

    var list: MutableList<Contact> = ArrayList()
    private lateinit var adapter: ContactsAdapter

    private  val manager = LinearLayoutManager(activity)
    lateinit var contact: Contact

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
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }


    private fun setChatAdapter(){

        //temp
        adapter = ContactsAdapter(list)
        list_chat.layoutManager = manager
        list_chat.adapter = adapter

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

        list.add(Contact(name = "11111", password = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name = "11222221",password = "133311", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name = "1122221",password = "114444411", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name = "112222211",password = "5555555", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name = "11111",password = "11111", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name = "11222221",password = "133311", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name = "1122221",password = "114444411", avatar = R.mipmap.ic_launcher_round))
        list.add(Contact(name = "112222211",password = "5555555", avatar = R.mipmap.ic_launcher_round))
    }

}