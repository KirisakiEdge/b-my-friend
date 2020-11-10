package com.example.b_my_friend.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogoutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sessionManager = this.activity?.let { SessionManager(it) }
        val intent = Intent(requireContext(), LoginActivity::class.java)
        val builder = AlertDialog.Builder(activity)

        builder.setMessage(R.string.dialog_text)
            .setPositiveButton(R.string.yes) { dialog, which ->
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                logout(sessionManager!!.fetchAuthToken()!!)

                sessionManager.saveAuthToken(null)

                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton(R.string.no) { dialog, which ->
                dismiss()
            }

        return builder.create()
    }

    private fun logout(token: String) {
        val call = NetworkService().getService().logout("Bearer $token")
        call.enqueue(object : Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful)
                    Log.e("logout", response.body()!!.message)
                else
                    response.message()
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.e("logout", t.message)
            }

        })
    }
}
