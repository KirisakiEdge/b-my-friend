package com.example.b_my_friend.networking


import com.example.b_my_friend.data.model.Contact
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface JSONPlaceHolderApi {
    @POST("api/auth/register")
    fun registerUser(@Query("name") name: String,
                     @Query("email") email: String,
                     @Query("password") password: String): Call<Contact>
    /*@GET("search.php")
    fun getDrinksByName(@Query("s") drinksName:String): Call<DrinksList>

    @GET("search.php")
    fun getDrinkByName(@Query("s") drinkName:String): Call<Drink>

    @GET("lookup.php")
    fun getDrinkById(@Query("i")idDrink:String): Call<DrinksList>*/
}