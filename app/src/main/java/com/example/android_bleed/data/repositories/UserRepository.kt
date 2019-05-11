package com.example.android_bleed.data.repositories

import android.content.Context
import android.os.Build
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.data.databases.UserDatabase
import com.example.android_bleed.data.models.User

class UserRepository(val context: Context) {

    fun getUserByUsername(userName: String): User? {
        val userDatabase = UserDatabase.getDatabase(context)
        return userDatabase?.userDao()?.getUser(userName)
    }

    fun registerUser(user: User) {
        val userDatabase = UserDatabase.getDatabase(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            user.password = AuthUtilities.encryptPassword(user.password)
        }
        userDatabase?.userDao()?.insert(user)
    }


}