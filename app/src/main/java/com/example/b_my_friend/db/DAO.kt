package com.example.b_my_friend.db

import androidx.room.*
import com.example.b_my_friend.data.model.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewUser(User: User)

    @Query("SELECT * FROM User")
    suspend fun following(): MutableList<User>

    @Query("DELETE FROM User")
    suspend fun clearUsers()

    @Query("SELECT * FROM User WHERE id IN (:id)")
    suspend fun loadUserById(id: String): User

}
@Dao
interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccount(currentAccount: User)

    @Query("SELECT * FROM User")
    suspend fun getAccountInfo(): User

    @Query("DELETE FROM User")
    suspend fun clearAccountInfo()

    @Query("UPDATE User SET name = :nick")
    suspend fun updateNickname(nick: String)

    @Query("UPDATE User SET img = :img, imgPath = :imgPath")
    suspend fun updateAvatar(img: String, imgPath: String)
}