package com.example.b_my_friend.networking


import android.content.Context
import android.util.Log
import android.widget.Toast
import coil.request.Disposable
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.LoggedInUser
import com.example.b_my_friend.data.model.User
import kotlinx.coroutines.*
import retrofit2.Call
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException


class UserAuth(val context: Context) {

    private val sessionManager = SessionManager(context)

    suspend fun auth(): User? {
        val call = NetworkService().getService().auth("Bearer ${sessionManager.fetchAuthToken()}")
        return suspendCoroutine { continuation ->
            call.clone().enqueue(object : Callback<LoggedInUser> {
                override fun onResponse(call: Call<LoggedInUser>, response: Response<LoggedInUser>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!.user)
                    }else{
                        continuation.resume(null)
                    }
                    //Log.e("auth", sessionManager.fetchAuthToken())
                   // Log.e("auth", response.message())
                }

                override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                    Toast.makeText(context, "Please, connect to the internet and restart app", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    suspend fun refreshToken(): User{
        val sessionManager = SessionManager(context)
        val call = NetworkService().getService().refreshToken("Bearer ${sessionManager.fetchAuthToken()}")
        return suspendCoroutine {continuation->
            call.enqueue(object : Callback<LoggedInUser>{
                override fun onResponse(call: Call<LoggedInUser>, response: Response<LoggedInUser>) {
                    if (response.isSuccessful) {
                        sessionManager.saveAuthToken(response.body()!!.accessToken)
                        continuation.resume(response.body()!!.user)
                        //Log.e("auth", "Token Refresh")
                    }else{
                       // Log.e("auth", response.message())
                    }
                }

                override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                    Log.e("refresh", t.message)
                }
            })
        }

    }
}