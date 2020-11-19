package com.example.b_my_friend.ui.chat

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b_my_friend.R
import com.example.b_my_friend.adapter.MessageAdapter
import com.example.b_my_friend.data.model.Chat
import kotlinx.android.synthetic.main.fragment_chat.view.*


class ChatFragment : Fragment() {

    var lChat: MutableList<Chat> = ArrayList()
    private val messageAdapter = MessageAdapter(lChat)
    private val manager = LinearLayoutManager(context)
    private lateinit var actionBar: ActionBar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_chat, container, false)

        actionBar= (requireActivity() as AppCompatActivity).supportActionBar!!

        val sourceBitmap = BitmapFactory.decodeResource(resources, requireArguments().getInt("Avatar"))
        if (sourceBitmap != null){
            val drawable = BitmapDrawable(
                resources, Bitmap.createScaledBitmap(createCircleBitmap(sourceBitmap),
                    actionBar.height, actionBar.height, true))
            actionBar.setIcon(drawable)
        }


        actionBar.title = arguments?.getString("NameSurname")
        actionBar.subtitle = arguments?.getString("email")


        v.list_of_message.setHasFixedSize(true)
        manager.stackFromEnd = true
        v.list_of_message.layoutManager = manager
        v.list_of_message.adapter = messageAdapter

        return  v
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        actionBar = (requireActivity() as AppCompatActivity).supportActionBar!!
        //Log.e("chat", actionBar.toString())
        actionBar.title = "My Contacts"
        actionBar.subtitle = ""
        actionBar.setIcon(0)

    }

    private fun createCircleBitmap(bitmapimg: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(
            bitmapimg.width,
            bitmapimg.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(
            0, 0, bitmapimg.width,
            bitmapimg.height
        )
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle(
            (bitmapimg.width / 2).toFloat(),
            (bitmapimg.height / 2).toFloat(), (bitmapimg.width / 2).toFloat(), paint
        )
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmapimg, rect, rect, paint)
        return output
    }
}