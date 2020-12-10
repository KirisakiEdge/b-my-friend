package com.example.b_my_friend.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.b_my_friend.MainActivity
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.LoggedInUser
import com.example.b_my_friend.db.AccountDataBase
import com.example.b_my_friend.networking.Count
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.login.forgotPassword.FPContainer
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    var currentUser: LoggedInUser? = null
    private val sessionManager by lazy {  SessionManager(applicationContext) }
    private val accountDB: AccountDataBase by lazy { Room.databaseBuilder(applicationContext,
        AccountDataBase::class.java, "account.db").build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (sessionManager.fetchAuthToken() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.emailIn)
        val password = findViewById<EditText>(R.id.passwordIn)
        val login = findViewById<Button>(R.id.registration)
        val register = findViewById<Button>(R.id.registerIn)

        loginViewModel = LoginViewModel(applicationContext)
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                emailIn.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })


        email.afterTextChanged {
            loginViewModel.loginDataChanged(
                email.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    email.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginCheck(email.text.toString(), password.text.toString())
                }
                false
            }
        }
        login.setOnClickListener {
            loginCheck(email.text.toString(), password.text.toString())
        }

        register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.putExtra("email", email.text.toString())
            intent.putExtra("password", password.text.toString())
            startActivity(intent)

        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, FPContainer::class.java)
            startActivity(intent)
        }
    }

    private fun loginCheck(email: String, password: String) {
        loading.visibility = View.VISIBLE
        GlobalScope.launch {
            currentUser = login(email, password)
            Log.e("login", currentUser.toString())
            if (currentUser != null) {
                sessionManager.saveAuthToken(currentUser!!.accessToken)
                accountDB.dao().saveAccount(currentUser!!.user)
                accountDB.close()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    private suspend fun login(email: String, password: String): LoggedInUser {
        val call = NetworkService().getService().login(email, password)
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<LoggedInUser> {
                override fun onResponse(call: Call<LoggedInUser>, response: Response<LoggedInUser>) {
                    if (response.body() != null) {
                        continuation.resume(response.body()!!)
                    }else{
                        loading.visibility = View.INVISIBLE
                        Toast.makeText(applicationContext, "Email or password not correct", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                    loading.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, "Please, connect to  internet", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}

