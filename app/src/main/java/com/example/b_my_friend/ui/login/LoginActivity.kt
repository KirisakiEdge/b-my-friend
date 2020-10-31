package com.example.b_my_friend.ui.login

import android.app.Activity
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
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.b_my_friend.MainActivity
import com.example.b_my_friend.R
import com.example.b_my_friend.data.model.Contact
import com.example.b_my_friend.networking.NetworkService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onStart() {
        super.onStart()
        ////////////////////FIREBASE/////////////////////////////////
      /*  val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        */
        ////////////////////FIREBASE/////////////////////////////////

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        ////////////////////FIREBASE/////////////////////////////////
       // val SIGN_IN_REQUEST_CODE = 1
       // var auth = FirebaseAuth.getInstance()
       // var database = Firebase.database


        //////////////////////////////////////////////////////////

        val email = findViewById<EditText>(R.id.emailId)
        val password = findViewById<EditText>(R.id.passwordId)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)
        val loading = findViewById<ProgressBar>(R.id.loading)


        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                emailId.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                  showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                 updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            val call = NetworkService().getService().registerUser(
                name = emailId.text.toString(),
                email = emailId.text.toString(),
                password = password.text.toString()
            )
            call.clone().enqueue(object : Callback<Contact> {
                override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_LONG).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("login", emailId.text.toString())
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                }

                override fun onFailure(call: Call<Contact>, t: Throwable) {
                    Log.e("TAG", t.message)
                    Toast.makeText(this@LoginActivity, "${t.message} t", Toast.LENGTH_LONG).show()
                }

            })

            //Complete and destroy login activity once successful
            /////////////////////////FireBase///////////////////////////////
            /*Thread{
                auth.createUserWithEmailAndPassword(username.text.toString(),password.text.toString())
                    .addOnCompleteListener { it ->
                        if (it.isSuccessful) {
                            var firebaseUser = auth.currentUser
                            var userid = firebaseUser!!.uid

                            var user = database.getReference("Users").child(userid)
                            var hashMap: HashMap<String,String> = HashMap()
                            hashMap["id"] = userid //put()
                            hashMap["username"] = username.text.toString()
                            hashMap["password"] = password.text.toString()
                            user.setValue(hashMap).addOnCompleteListener { it ->
                                if (it.isSuccessful){
                                    /////////////////////////////////////
                                    runOnUiThread {
                                        Toast.makeText(this, "Registration success, Click log again", Toast.LENGTH_LONG).show()
                                    }
                                    /////////////////////////////
                                }
                            }
                        }else{
                            auth.signInWithEmailAndPassword(username.text.toString(),password.text.toString())
                            .addOnCompleteListener { it ->
                                if (it.isSuccessful){
                                    intent.putExtra("login", username.text.toString())
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    runOnUiThread {
                                        Toast.makeText(this, "Pleas connect Internet", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
            }.start()*/

            //////////////////////////////////


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
                        loginViewModel.login(
                            emailId.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(email.text.toString(), password.text.toString())
            }
            register.setOnClickListener {
                /*val fragment: Fragment = RegisterFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()*/
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = emailId.text.toString()
        // TODO : initiate successful logged in experience

       /* Thread{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("login", username.text.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Log.e("TAG", intent.extras!!["login"].toString())
            startActivity(intent)
            finish()
        }.start()*/

        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}