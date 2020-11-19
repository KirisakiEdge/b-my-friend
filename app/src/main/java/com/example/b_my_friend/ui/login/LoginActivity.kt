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
import com.example.b_my_friend.MainActivity
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.LoggedInUser
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.login.forgotPassword.FPContainer
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    var currentUser: LoggedInUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(applicationContext)
        if (sessionManager.fetchAuthToken() != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.emailIn)
        val password = findViewById<EditText>(R.id.passwordIn)
        val login = findViewById<Button>(R.id.register)
        val register = findViewById<Button>(R.id.registerIn)
        val loading = findViewById<ProgressBar>(R.id.loading)

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
                        login(email.text.toString(), password.text.toString())
                }
                false
            }
        }


        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            login(email.text.toString(), password.text.toString())
            GlobalScope.launch {
                delay(2000)

                Log.e("login", currentUser.toString())
                if (currentUser!= null) {
                    sessionManager.saveAuthToken(currentUser!!.accessToken)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    runOnUiThread {
                        loading.visibility = View.INVISIBLE
                        Toast.makeText(this@LoginActivity, "Please try again", Toast.LENGTH_LONG).show()
                    }
                }
            }
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

    private fun login(email: String, password: String){

        val call = NetworkService().getService().login(email, password)
        Thread{
            try {
                if (call.clone().execute().isSuccessful) {
                    while (currentUser == null) {
                        currentUser = call.clone().execute().body()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Email or password wrong", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Internet connection not found", Toast.LENGTH_LONG).show()
                }
                //Log.e("login", e.toString())
            }
        }.start()
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

