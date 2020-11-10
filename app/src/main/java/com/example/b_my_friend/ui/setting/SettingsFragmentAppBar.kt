package com.example.b_my_friend.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.b_my_friend.R
import com.example.b_my_friend.ui.page.PageViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.android.synthetic.main.fragment_settings_app_bar_t.*



class SettingsFragmentAppBar : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel= ViewModelProviders.of(requireActivity()).get(PageViewModel::class.java)
        viewModel.getCurrentUser().observe(viewLifecycleOwner, Observer {
           // Log.e("setting", it.toString())
            settingsNick.background = null
            settingsNick.text = it.name
            settingsEmail.background = null
            settingsEmail.text = it.email
        })

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
}