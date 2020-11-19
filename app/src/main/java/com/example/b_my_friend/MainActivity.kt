package com.example.b_my_friend

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.networking.Count
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.networking.UserAuth
import com.example.b_my_friend.ui.page.PageViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val sessionManager = SessionManager(applicationContext)
        if (sessionManager.getTheme()) {
            theme.applyStyle(R.style.AppColors, true)
        } else {
            theme.applyStyle(R.style.LightColors, true)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navView: NavigationView = findViewById(R.id.nav_view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()
        val navHeader = navView.getHeaderView(0)
        val userEmail = navHeader.userEmail
        val userNS = navHeader.userNick


        val viewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            currentUser = UserAuth(applicationContext).auth()
            viewModel.setDataUser(currentUser!!)
            //Log.e("page", viewModel.getCurrentUser().value.toString())
        }


        viewModel.getCurrentUser().observe(this, Observer {
            //Log.e("page", it.toString())
            userNS.text = it.name
            userEmail.text = it.email

            if (it.emailVerifiedAt == null) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Please, verify your email in Setting",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_page, R.id.nav_contacts, R.id.nav_groups, R.id.nav_setting, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val switchContainer = navView.menu.findItem(R.id.switch_container)
        val actionView = switchContainer.actionView
        val themeSwitcher =  actionView.findViewById<SwitchCompat>(R.id.themeSwitcher)
        themeSwitcher.isChecked = sessionManager.getTheme()
        themeSwitcher.setOnClickListener {
            if (sessionManager.getTheme()){
                theme.applyStyle(R.style.AppTheme, true)
            }else{
                theme.applyStyle(R.style.LightTheme, true)
            }
            sessionManager.setTheme(!sessionManager.getTheme())
            Toast.makeText(applicationContext, " CLICK", Toast.LENGTH_LONG).show()
            super.recreate()
        }

        refreshActivity.setOnRefreshListener {
            refreshActivity.postDelayed(Runnable {
                refreshActivity.isRefreshing = false }, 1000)
            refreshActivity.postDelayed(Runnable {
                super.recreate()}, 1200)

            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}