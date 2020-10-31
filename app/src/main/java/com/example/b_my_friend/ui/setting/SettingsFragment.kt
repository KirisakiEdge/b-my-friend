package com.example.b_my_friend.ui.setting

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.preference.PreferenceFragmentCompat
import com.example.b_my_friend.R
import kotlinx.android.synthetic.main.app_bar_main.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}