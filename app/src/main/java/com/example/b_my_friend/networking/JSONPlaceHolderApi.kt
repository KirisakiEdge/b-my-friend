package com.example.b_my_friend.networking

import com.example.b_my_friend.data.model.Account
import com.example.b_my_friend.data.model.AllFollow
import com.example.b_my_friend.data.model.AllUser
import com.example.b_my_friend.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.http.*

interface JSONPlaceHolderApi {

    //Auth

    @POST("/api/auth/register")
    fun registerUser(@Query("name") name: String,
                     @Query("email") email: String,
                     @Query("password") password: String): Call<Account>

    @POST("/api/auth/login")
    fun login(@Query("email") email: String,
              @Query("password") password: String): Call<LoggedInUser>

    @POST("/api/auth/me")
    fun auth(@Header("Authorization") token: String): Call<LoggedInUser>


    @POST("/api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<Message>


    //Email

    @POST("/api/email/send-verification ")
    fun sendEmailVerification(@Header("Authorization")token: String): Call<Message>


    //android-auth

    @POST("/api/auth/android/reset-password")
    fun resetPassword(@Query("email") email: String): Call<Message>

    @POST("/api/auth/android/reset-code-check")
    fun checkResetCode(@Query("email") email: String,
                       @Query("code") code: String): Call<Message>

    @POST("/api/auth/android/password-change")
    fun changePassword(@Query("email") email: String,
                       @Query("code") code: String,
                       @Query("password") password: String,
                       @Query("password_confirmation") passwordConfirmation: String): Call<Message>


    //Followers
    @GET("/api/followers")
    fun getAllFollowers(@Header("Authorization") token: String, @Query("page") numberOfPage: String): Call<AllUser>

    @GET("/api/followers/count-all")
    fun getCountOfFollowers(@Header("Authorization") token: String): Call<Count>


    //Following
    @GET("/api/following")
    fun getAllFollowing(@Header("Authorization") token: String, @Query("page") numberOfPage: String): Call<AllUser>

    @GET("/api/following/count-all")
    fun getCountOfFollowing(@Header("Authorization") token: String): Call<Count>

    @POST("/api/following")
    fun followToSomeUser(@Header("Authorization") token: String, @Query("user_id") userId: String): Call<Message>

    @DELETE("/api/following/{user_id}")
    fun unFollowToSomeUser(@Header("Authorization") token: String, @Path("user_id") userId: String): Call<Message>


    //Users
    @GET("/api/users")
    fun getAllUsers(@Header("Authorization") token: String, @Query("page") numberOfPage: String): Call<AllUser>
}
data class Count(var count:String)
data class Message(var message: String)