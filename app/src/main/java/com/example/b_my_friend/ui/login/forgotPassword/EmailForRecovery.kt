package com.example.b_my_friend.ui.login.forgotPassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.b_my_friend.R
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_email_for_recovery.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EmailForRecovery : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkEmail.setOnClickListener{
            emailRecoveryLoading.visibility = View.VISIBLE
            val call = NetworkService().getService().resetPassword(editTextEmailAddress.text.toString())
            call.enqueue(object : Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    //Log.e("emailRecovery", response.body()!!.message)

                    val bundle = bundleOf("email" to editTextEmailAddress.text.toString())
                    emailRecoveryLoading.visibility = View.INVISIBLE
                    view.findNavController().navigate(R.id.action_emailForRecovery2_to_codeForVerify2, bundle)
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    emailRecoveryLoading.visibility = View.INVISIBLE
                    //Log.e("emailRecovery", t.message)
                    Toast.makeText(requireContext(), "Please, connect to the internet", Toast.LENGTH_LONG).show()
                }
            })

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_email_for_recovery, container, false)

        val email = v.findViewById<EditText>(R.id.editTextEmailAddress)
        val checkEmail = v.findViewById<Button>(R.id.checkEmail)

        val loginViewModel = LoginViewModel(requireContext())

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username
            checkEmail.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                email.error = getString(loginState.emailError)
            }
        })

        email.afterTextChanged {
            loginViewModel.emailVerify(editTextEmailAddress.text.toString()) }
        return v
    }

}

private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

