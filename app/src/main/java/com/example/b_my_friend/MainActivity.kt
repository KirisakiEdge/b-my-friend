package com.example.b_my_friend

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.db.AccountDataBase
import com.example.b_my_friend.networking.UserAuth
import com.example.b_my_friend.ui.page.PageViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var chatAppbar: LinearLayout
    private lateinit var chatAvatar: CircleImageView
    private lateinit var chatTitle: TextView
    private lateinit var chatSubtitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        val accountDB: AccountDataBase = Room.databaseBuilder(
        applicationContext, AccountDataBase::class.java, "account.db").build()
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

        chatAppbar = findViewById(R.id.chat_app_bar_info)
        chatAvatar = findViewById(R.id.chat_avatar)
        chatTitle = findViewById(R.id.chat_title)
        chatSubtitle = findViewById(R.id.chat_subtitle)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()
        val navHeader = navView.getHeaderView(0)
        val userEmail = navHeader.userEmail
        val userNS = navHeader.userNick
        val userAvatar = navHeader.userAvatar


        val viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            var currentUser = accountDB.dao().getAccountInfo()
            var bitmap = BitmapFactory.decodeFile(currentUser.imgPath)
            setAccountInfo(currentUser, bitmap,  userEmail, userNS, userAvatar)
            viewModel.setDataUser(currentUser)
            if (UserAuth(applicationContext).testingToken() != null){
                currentUser =  UserAuth(applicationContext).testingToken()!!
                if (currentUser.avatar != ""){
                    val imageByteArray = Base64.decode(currentUser.avatar, Base64.DEFAULT)
                    bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                    val file = File(applicationContext.cacheDir.absolutePath, "${currentUser.id}.jpg")
                    file.writeBitmap(bitmap, Bitmap.CompressFormat.JPEG, 85)
                    currentUser.imgPath = file.absolutePath
                    accountDB.dao().clearAccountInfo()
                    accountDB.dao().saveAccount(currentUser)
                }
                setAccountInfo(currentUser, bitmap,  userEmail, userNS, userAvatar)
                viewModel.setDataUser(currentUser)
            }
            accountDB.close()
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_my_page,
            R.id.nav_contacts,
            R.id.nav_groups,
            R.id.nav_setting,
            R.id.nav_logout), drawerLayout)

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
            super.recreate()
        }

        refreshActivity.setOnRefreshListener {    //bad, i guess
            refreshActivity.postDelayed(Runnable {
                refreshActivity.isRefreshing = false
            }, 1000)
            refreshActivity.postDelayed(Runnable {
                super.recreate()
            }, 1200)

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

    private fun setAccountInfo(account: User, bitmap: Bitmap?, userEmail: TextView, userNS: TextView, userAvatar: CircleImageView){
        GlobalScope.launch(Dispatchers.Main) {
            userNS.text = account.name
            userEmail.text = account.email
            if (bitmap != null)
                userAvatar.setImageBitmap(bitmap)
            if (account.emailVerifiedAt == null) {
                Snackbar.make(findViewById(android.R.id.content), "Please, verify your email in Setting", Snackbar.LENGTH_LONG).show()
            }
        }
    }
    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }


    fun setActionBarChat(view: View, user: User, followersId: ArrayList<String>){
        if (user.avatar != ""){
            val bitmap = BitmapFactory.decodeFile(user.imgPath)
            this.chatAvatar.setImageBitmap(bitmap)
        }else{
            this.chatAvatar.setImageResource(R.mipmap.temp_icon)
        }
        this.chatTitle.text = user.name
        this.chatSubtitle.text = user.email

        if(user.name != "") {
            this.chatAppbar.setOnClickListener {
                val bundle = bundleOf(
                    "userId" to user.id,
                    "userName" to user.name,
                    "userAvatar" to user.imgPath,
                    "idList" to followersId
                )
                view.findNavController().navigate(R.id.action_chatFragment_to_UserPageFragment, bundle)
            }
        }
    }
}