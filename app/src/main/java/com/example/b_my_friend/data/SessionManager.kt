package com.example.b_my_friend.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("LoggedUser", Context.MODE_PRIVATE)
    private var theme: SharedPreferences = context.getSharedPreferences("Theme", Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val THEME = "theme"
    }

    fun setTheme(boolean: Boolean){
        val editor = theme.edit()
        editor.putBoolean(THEME, boolean)
        editor.apply()
    }

    fun getTheme(): Boolean{
        return theme.getBoolean(THEME, true)
    }



    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }


}