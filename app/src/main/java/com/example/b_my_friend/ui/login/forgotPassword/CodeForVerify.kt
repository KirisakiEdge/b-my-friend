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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.b_my_friend.R
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_code_for_verify.*
import kotlinx.android.synthetic.main.fragment_email_for_recovery.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CodeForVerify : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkCode.setOnClickListener{
            checkCodeLoading.visibility = View.VISIBLE
            val email = arguments?.getString("email")

            val call = NetworkService().getService().checkResetCode(email!!, editCode.text.toString())
            call.enqueue(object : Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful) {
                        GlobalScope.launch {
                            delay(1000)
                            val bundle = bundleOf("email" to email, "code" to editCode.text.toString())
                            //checkCodeLoading.visibility = View.INVISIBLE
                            view.findNavController().navigate(R.id.action_codeForVerify2_to_passwordRecovery2, bundle)
                        }
                    }else {
                        checkCodeLoading.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "Wrong Code", Toast.LENGTH_LONG).show()
                        //Log.e("codeVerify", response.message())
                    }
                }
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e("codeVerify", t.message)
                    checkCodeLoading.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Please, connect to the internet", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_code_for_verify, container, false)

        val editCode = v.findViewById<EditText>(R.id.editCode)
        val checkCode = v.findViewById<Button>(R.id.checkCode)

        val loginViewModel = LoginViewModel(requireContext())

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username
            checkCode.isEnabled = loginState.isDataValid

            if (loginState.codeError != null) {
                editCode.error = getString(loginState.codeError)
            }
        })

        editCode.afterTextChanged {
            loginViewModel.resetCode(editCode.text.toString()) }

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