package com.example.android_bleed.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_bleed.data.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM UserTable WHERE user_name = :userName")
    fun getUser(userName: String): User

    @Query("SELECT * FROM UserTable")
    fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
//
//    @Query("DELETE from UserTable")
//    fun deleteAll()

}