package com.example.android_bleed.authentication.domain

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.models.User
import com.example.android_bleed.data.repositories.UserRepository
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.flowsteps.UserAction
import com.example.android_bleed.authentication.AuthUtilities

class LoginAction : UserAction.UserApplicationAction() {

    override fun execute(application: Application): LiveData<LegendResult> {

        val data = MutableLiveData<LegendResult>()
        val thread = Thread(Runnable {

            val userName = dataBundle.getString(User.EXTRA_USERNAME)
            val password = dataBundle.getString(User.EXTRA_PASSWORD)

            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                data.postValue(LegendResult.FailResult("Please provide a username and a password"))
                return@Runnable
            }

            val user = UserRepository(application).getUserByUsername(userName!!.trim())

            user?.apply {
                if (AuthUtilities.isPasswordValid(password!!, this.password)) {
                    val resource = LoginResult(user)
                    data.postValue(resource)
                    return@Runnable
                } else {
                    data.postValue(LegendResult.FailResult("The entered password is incorrect"))
                }
            }
            data.postValue(LegendResult.FailResult("No user found with username \"$userName\""))
        })
        thread.start()
        return data
    }


    data class LoginResult(val user: User) : LegendResult(Status.COMPLETED)
}