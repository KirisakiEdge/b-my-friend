package com.example.b_my_friend.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.b_my_friend.R

open class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}