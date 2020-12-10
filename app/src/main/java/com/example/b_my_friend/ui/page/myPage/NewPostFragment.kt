package com.example.b_my_friend.ui.page.myPage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import kotlinx.android.synthetic.main.fragment_new_post.*
import kotlinx.android.synthetic.main.fragment_new_post.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewPostFragment : Fragment() {

    private val token by lazy { SessionManager(requireActivity()).fetchAuthToken() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //when update post, not create
        if (arguments != null){
            view.sendPost.text = getString(R.string.update)
            val title= requireArguments().getString("title")
            view.newPostTitle.setText(title)
            val text = requireArguments().getString("text")
            view.newPostText.setText(text)
        }

        val postViewModel = PostViewModel()
        postViewModel.postFormState.observe(viewLifecycleOwner, Observer {
            val postState = it ?: return@Observer

            // disable login button unless both username / password is valid
            view.sendPost.isEnabled = postState.isDataValid

            if (postState.titleError != null) {
                view.newPostTitle.error = getString(postState.titleError)
            }
            if (postState.textError != null) {
                view.newPostText.error = getString(postState.textError)
            }
        })

        view.newPostTitle.afterTextChanged {
            postViewModel.newPost(
                view.newPostTitle.text.toString(),
                view.newPostText.text.toString()
            )
        }

        view.newPostText.apply {
            afterTextChanged {
                postViewModel.newPost(
                    view.newPostTitle.text.toString(),
                    view.newPostText.text.toString()
                )
            }
        }

        view.sendPost.setOnClickListener {
            // if send else update post
            if (view.sendPost.text == "Send"){
                sendPost(token!!, view.newPostTitle.text.toString(), view.newPostText.text.toString())
            }else{
                updatePost(token!!, requireArguments().getString("blogId")!!, newPostTitle.text.toString(), newPostText.text.toString())
            }
            requireActivity().recreate()
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_post, container, false)
    }

    private fun sendPost(token: String, title: String, text: String) {
        val call = NetworkService().getService().createPost("Bearer $token", title, text)
        call.enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                //Log.e("newPost", response.message())
            }
            override fun onFailure(call: Call<Message>, t: Throwable) {
                //Log.e("newPost", t.message)
            }

        })
    }


    private fun updatePost(token: String, id: String, title: String, text: String){
        val call = NetworkService().getService().updatePost("Bearer $token", id, title, text)
        call.enqueue(object : Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {}
            override fun onFailure(call: Call<Message>, t: Throwable) {}
        })
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}