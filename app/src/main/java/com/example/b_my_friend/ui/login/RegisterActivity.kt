package com.example.b_my_friend.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.b_my_friend.R
import com.example.b_my_friend.data.model.Account
import com.example.b_my_friend.networking.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val register = findViewById<Button>(R.id.register)

        if (intent.extras?.get("email") != null){
            email.setText(intent.extras?.get("email").toString())
        }
        if(intent.extras?.get("password") != null){
            password.setText(intent.extras?.get("password").toString())
        }

        loginViewModel = LoginViewModel(applicationContext)

        loginViewModel.loginFormState.observe(this@RegisterActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            register.isEnabled = loginState.isDataValid
            if (loginState.usernameError !=null){
                username.error = getString(loginState.usernameError)
            }
            if (loginState.emailError != null) {
                email.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })



        username.afterTextChanged {
            loginViewModel.registerDataChanged(
                username.text.toString(),
                email.text.toString(),
                password.text.toString())
        }

        email.afterTextChanged {
            loginViewModel.registerDataChanged(
                username.text.toString(),
                email.text.toString(),
                password.text.toString())
        }

        password.apply {
            afterTextChanged {
                loginViewModel.registerDataChanged(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
            }

            register.setOnClickListener {
                register(username.text.toString(),
                    email.text.toString(),
                    password.text.toString())
                finish()

            }
        }
    }


    private fun register(username: String, email: String, password: String) {
        val call = NetworkService().getService()
            .registerUser(name = username, email = email, password = password)
        call.clone().enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_LONG)
                        .show()
                } else {
                    Log.e("register", response.message())
                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                Log.e("register", t.message)
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