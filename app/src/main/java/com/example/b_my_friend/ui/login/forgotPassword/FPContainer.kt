package com.example.b_my_friend.ui.login.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.b_my_friend.R

class FPContainer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_p_container)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_forgot_password)
        val navController = navHostFragment!!.findNavController()

    }
}