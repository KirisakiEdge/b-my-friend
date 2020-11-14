package com.example.b_my_friend.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.page.PageViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_settings_app_bar_t.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsFragmentAppBar : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val token = sessionManager.fetchAuthToken()!!
        val viewModel= ViewModelProviders.of(requireActivity()).get(PageViewModel::class.java)

        viewModel.getCurrentUser().observe(viewLifecycleOwner, Observer {
           // Log.e("setting", it.toString())
            settingsNick.background = null
            settingsNick.text = it.name
            settingsEmail.background = null
            settingsEmail.text = it.email

            if (it.emailVerifiedAt == null){
                verify.visibility = View.VISIBLE
            }
            if (verify.visibility == View.VISIBLE){
                verify.setOnClickListener {
                    sendEmailVerification(token)
                }
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fab_setting.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_settings_app_bar_t, container, false)
        requireFragmentManager().beginTransaction()
            .replace(R.id.settingsContainer, SettingsFragment())
            .commit();

        return v

    }

    private fun sendEmailVerification(token: String){
        val call = NetworkService().getService().sendEmailVerification("Bearer $token")
        call.enqueue(object: Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                verify.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Mail for verify already send on your email", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(context, "Please, connect to the internet", Toast.LENGTH_LONG).show()
            }
        })

    }
}