package com.example.android_bleed.data.repositories

import android.content.Context
import com.example.android_bleed.data.databases.UserDatabase
import com.example.android_bleed.data.models.User

class UserRepository(val context: Context) {

    fun getUserByUsername(userName: String): User? {
        val userDatabase = UserDatabase.getDatabase(context)
        return userDatabase?.userDao()?.getUser(userName)
    }

    fun registerUser(user: User) {
        val userDatabase = UserDatabase.getDatabase(context)

        userDatabase?.userDao()?.insert(user)
    }


}