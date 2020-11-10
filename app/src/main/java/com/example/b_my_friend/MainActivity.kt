package com.example.b_my_friend


import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.networking.UserAuth
import com.example.b_my_friend.ui.page.PageViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var currentUser: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navController = findNavController(R.id.nav_host_fragment)
        val navHeader = navView.getHeaderView(0)
        val userEmail = navHeader.userEmail
        val userNS = navHeader.userNick


        val viewModel= ViewModelProviders.of(this).get(PageViewModel::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            currentUser = UserAuth(applicationContext).auth()
            if(currentUser == null) {
                currentUser = UserAuth(applicationContext).refreshToken()
            }
            viewModel.setDataUser(currentUser!!)
            //Log.e("page", viewModel.getCurrentUser().value.toString())
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        viewModel.getCurrentUser().observe(this, Observer {
            Log.e("page", it.toString())
            userNS.text = it.name
            userEmail.text = it.email
        })

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_page, R.id.nav_contacts, R.id.nav_groups, R.id.nav_setting, R.id.nav_login
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}