package com.example.b_my_friend.networking


import com.example.b_my_friend.data.model.Account
import com.example.b_my_friend.data.model.LoggedInUser
import com.example.b_my_friend.data.model.User
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query



interface JSONPlaceHolderApi {
    @POST("api/auth/register")
    fun registerUser(@Query("name") name: String,
                     @Query("email") email: String,
                     @Query("password") password: String): Call<Account>

    @POST("api/auth/login")
    fun login(@Query("email") email: String,
              @Query("password") password: String): Call<LoggedInUser>

    @POST("api/auth/me")
    fun auth(@Header("Authorization") token: String): Call<LoggedInUser>

    @POST("api/auth/refresh")
    fun refreshToken(@Header("Authorization") token: String): Call<LoggedInUser>

    @POST("api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<Message>
}

data class Message(var message: String)