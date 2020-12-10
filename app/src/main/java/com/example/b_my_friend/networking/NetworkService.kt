package com.example.b_my_friend.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    companion object {
        private var mInstance: NetworkService? = null
        private const val BASE_URL = "http://morning-beach-19824.herokuapp.com"
        val instance: NetworkService?
            get() {
                if (mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance
            }
    }

    fun getService(): JSONPlaceHolderApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(JSONPlaceHolderApi::class.java)
    }
}