package com.example.android_bleed.authentication.domain

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.models.User
import com.example.android_bleed.data.repositories.UserRepository
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.flowsteps.UserAction

class RegisterAction : UserAction.UserApplicationAction() {
    override fun execute(application: Application): LiveData<LegendResult> {

        val data = MutableLiveData<LegendResult>()
        val thread = Thread(Runnable {

            val repository = UserRepository(application)
            val user = dataBundle.getParcelable<User>(User.EXTRA_USER)

            if (user == null) {
                data.postValue(LegendResult.FailResult("A user was not provided"))
                return@Runnable
            }

            repository.registerUser(user = user)

            val registerResult = RegisterResult(user)
            registerResult.bundle.putParcelable(User.EXTRA_USER, user)

            data.postValue(registerResult)

        })
        thread.start()
        return data
    }

    data class RegisterResult(val user: User) : LegendResult(Status.COMPLETED)

}