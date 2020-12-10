package com.example.b_my_friend.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.b_my_friend.data.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDataBase: RoomDatabase() {
    abstract fun dao(): UserDAO
}

@Database(entities = [User::class], version = 1)
abstract class AccountDataBase : RoomDatabase() {
    abstract fun dao(): AccountDAO
}