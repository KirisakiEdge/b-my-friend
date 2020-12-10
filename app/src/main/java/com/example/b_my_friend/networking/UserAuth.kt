package com.example.b_my_friend.networking

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.LoggedInUser
import com.example.b_my_friend.data.model.User
import retrofit2.Call
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import retrofit2.Callback
import retrofit2.Response


class UserAuth(val context: Context) {
    private val sessionManager = SessionManager(context)

    suspend fun testingToken(): User? {
        val call = NetworkService().getService().testingToken("Bearer ${sessionManager.fetchAuthToken()}")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<LoggedInUser> {
                override fun onResponse(call: Call<LoggedInUser>, response: Response<LoggedInUser>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!.user)
                    }else{
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show()
                        continuation.resume(null)
                    }
                    //Log.e("auth", sessionManager.fetchAuthToken())
                   // Log.e("auth", response.message())
                }

                override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                    Toast.makeText(context, "Please, connect to  internet", Toast.LENGTH_LONG).show()
                    continuation.resume(null)
                }
            })
        }
    }
}