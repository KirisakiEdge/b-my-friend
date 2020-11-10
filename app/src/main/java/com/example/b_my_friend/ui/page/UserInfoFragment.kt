package com.example.b_my_friend.ui.page

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.b_my_friend.R
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.networking.UserAuth
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserInfoFragment : Fragment() {

    private var currentUser: User? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel= ViewModelProviders.of(requireActivity()).get(PageViewModel::class.java)
        Log.e("page", viewModel.getCurrentUser().value.toString())
        viewModel.getCurrentUser().observe(viewLifecycleOwner, Observer {
            //Log.e("page", it.toString())
            pageNick.background = null
            pageNick.text = it.name
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        GlobalScope.launch(Dispatchers.Main) {
            currentUser = UserAuth(requireContext()).auth()

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatar.setOnClickListener { Toast.makeText(activity, "CLICK CLACK", Toast.LENGTH_LONG).show() }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

}