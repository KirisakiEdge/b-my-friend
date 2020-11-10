package com.example.b_my_friend.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.MessageAdapter
import com.example.b_my_friend.data.model.Chat
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {


    var lChat: MutableList<Chat> = ArrayList()
    private val messageAdapter = MessageAdapter(lChat)
    private val manager = LinearLayoutManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        nameSChat.text = intent.extras!!["NameSurname"].toString()
        chatAvatar.setImageResource(intent.extras!!["Avatar"] as Int)
        val userid = intent.extras!!["id"].toString()


        list_of_message.setHasFixedSize(true)
        manager.stackFromEnd = true
        list_of_message.layoutManager = manager
        list_of_message.adapter = messageAdapter


        backChat.setOnClickListener {
            onBackPressed()

        }



       /* chatSend.setOnClickListener {
            var msg = chatEnter.text.toString()
            Log.e("TAG", msg)
            Thread {
                if (msg != "") {
                    sendMessage(user!!.uid, userid, msg)
                }
            }.start()

            chatEnter.setText("")
        }*/

        /*Thread{
            reference = Firebase.database.getReference("Users").child(userid)
            reference.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    readMessages(user!!.uid, userid)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }.start()
*/
    }

   /* private fun sendMessage(sender:String, receiver: String, message: String){
            reference = Firebase.database.reference

            var hashMap = HashMap<String, String>()
            hashMap["sender"] = sender
            hashMap["receiver"] = receiver
            hashMap["message"] = message

            reference.child("Chats").push().setValue(hashMap)
    }
*/

   /* private fun readMessages(myid:String, userid:String){
        reference = Firebase.database.getReference("Chats")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                lChat.clear()
                snapshot.children.forEach {
                    val chat = it.getValue(Chat::class.java)!!
                    if (chat.receiver == myid && chat.sender == userid ||
                        chat.receiver == userid && chat.sender == myid){
                        lChat.add(chat)
                    }

                    messageAdapter = MessageAdapter(applicationContext, lChat)
                    list_of_message.adapter = messageAdapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }*/
}