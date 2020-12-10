package com.example.b_my_friend.networking

import com.example.b_my_friend.data.model.*
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
    fun testingToken(@Header("Authorization") token: String): Call<LoggedInUser>


    @POST("/api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<Message>


    //Email

    @POST("/api/email/change ")
    fun updateEmail(@Header("Authorization")token: String,
                    @Query("email") email: String,
                    @Query("password") password: String): Call<Message>

    @POST("/api/email/send-verification")
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

    //users/feeds(photo)
    @FormUrlEncoded   //for image base64 encode
    @POST("/api/users/feeds")
    fun createFeed(@Header("Authorization") token: String,
                   @Field("description") desc: String,       //for image base64 encode
                   @Field("img") img: String): Call<Message> //for image base64 encode

    @GET("/api/users/feeds")
    fun getAllFeeds(@Header("Authorization") token: String, @Query("page") numberOfPage: String): Call<AllFeeds>

    @GET("/api/users/feeds/{id}")
    fun getFeed(@Header("Authorization") token: String, @Path("id") feedId: String): Call<Feed>

    @PUT("/api/users/feeds/{id}")
    fun updateFeed(@Header("Authorization") token: String,
                   @Path("id") feedId: String,
                   @Query("description") desc: String): Call<Message>

    @DELETE("/api/users/feeds/{id}")
    fun deleteFeed(@Header("Authorization") token: String, @Path("id") feedId: String): Call<Message>

    @GET("/api/users/feeds/of/{user_id}")
    fun getAllFeedsOfSomeUser(@Header("Authorization") token: String,
                              @Path("user_id") userId: String,
                              @Query("page") numberOfPage: String,
                              ): Call<AllFeeds>


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


    //users/posts
    @GET("/api/users/posts")
    fun getAllPosts(@Header("Authorization") token: String, @Query("page") numberOfPage: String): Call<AllPosts>

    @POST("/api/users/posts")
    fun createPost(@Header("Authorization") token: String,
                   @Query("title") title: String,
                   @Query("body") body: String): Call<Message>

    @GET("/api/users/posts/{id}")
    fun getPost(@Header("Authorization") token: String, @Path("id") postId: String): Call<Post>

    @PUT("/api/users/posts/{id}")
    fun updatePost(@Header("Authorization") token: String,
                   @Path("id") postId: String,
                   @Query("title") title: String,
                   @Query("body") body: String): Call<Message>

    @DELETE("/api/users/posts/{id}")
    fun deletePost(@Header("Authorization") token: String, @Path("id") postId: String): Call<Message>

    @GET("/api/users/posts/of/{user_id}")
    fun getAllPostsOfSomeUser(@Header("Authorization") token: String,
                              @Path("user_id") userId: String,
                              @Query("page") numberOfPage: String): Call<AllPosts>



    //Users
    @GET("/api/users")
    fun getAllUsers(@Header("Authorization") token: String, @Query("page") numberOfPage: String): Call<AllUser>

    @PUT("/api/users")
    fun updateNickname(@Header("Authorization") token: String, @Query("name") name: String): Call<Message>

    @FormUrlEncoded  //for image base64 encode
    @PATCH("/api/users/upload-photo")
    fun updateAvatar(@Header("Authorization") token: String, @Field("img") img: String): Call<Message>


}
data class Count(var count:String)
data class Message(var message: String)