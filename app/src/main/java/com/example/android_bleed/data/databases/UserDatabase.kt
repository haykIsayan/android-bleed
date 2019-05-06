package com.example.android_bleed.data.databases

import android.content.Context
import androidx.room.*
import com.example.android_bleed.data.daos.UserDao
import com.example.android_bleed.data.models.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {


    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()
            }
            return INSTANCE
        }
    }



}