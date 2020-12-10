package com.example.b_my_friend.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.db.AccountDataBase
import com.example.b_my_friend.db.UserDataBase
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogoutDialogFragment : DialogFragment() {

    private val accountDB: AccountDataBase by lazy {
        Room.databaseBuilder(requireContext(), AccountDataBase::class.java, "account.db").build() }
    private val userDB: UserDataBase by lazy {
        Room.databaseBuilder(requireContext(), UserDataBase::class.java, "user.db").build() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sessionManager = SessionManager(requireActivity())
        val intent = Intent(requireContext(), LoginActivity::class.java)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(R.string.dialog_text)
            .setPositiveButton(R.string.yes) { dialog, which ->
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                logout(sessionManager.fetchAuthToken()!!)
                GlobalScope.launch{
                    accountDB.dao().clearAccountInfo()
                    userDB.dao().clearUsers()
                    accountDB.close()
                    userDB.close()
                }
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
            override fun onResponse(call: Call<Message>, response: Response<Message>) {}
            override fun onFailure(call: Call<Message>, t: Throwable) {}
        })
    }
}
