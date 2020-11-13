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
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.b_my_friend.R
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_password_recovery.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PasswordRecovery : Fragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savePassword.setOnClickListener {
            val email = arguments?.getString("email")
            val code = arguments?.getString("code")

            val call = NetworkService().getService().changePassword(
                email!!, code!!, newPassword.text.toString(), passwordConfirm.text.toString())

            call.enqueue(object : Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Password change confirm", Toast.LENGTH_LONG).show()
                        requireActivity().finish()
                        //Log.e("passwordRecovery", response.body()!!.message)
                    }
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e("passwordRecovery", t.message)
                    Toast.makeText(requireContext(), "Please, connect to the internet", Toast.LENGTH_LONG).show()
                }

            })

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_password_recovery, container, false)

        val newPassword = v.findViewById<EditText>(R.id.newPassword)
        val passwordConfirm = v.findViewById<EditText>(R.id.passwordConfirm)
        val savePassword = v.findViewById<Button>(R.id.savePassword)

        val loginViewModel = LoginViewModel(requireContext())

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username
            savePassword.isEnabled = loginState.isDataValid

            if (loginState.newPasswordError != null) {
                newPassword.error = getString(loginState.newPasswordError)
            }
            if (loginState.passwordConfirmError != null) {
                passwordConfirm.error = getString(loginState.passwordConfirmError)
            }
        })

        newPassword.afterTextChanged {
            loginViewModel.resetPassword(newPassword.text.toString(), passwordConfirm.text.toString()) }

        passwordConfirm.afterTextChanged {
            loginViewModel.resetPassword(newPassword.text.toString(), passwordConfirm.text.toString()) }


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