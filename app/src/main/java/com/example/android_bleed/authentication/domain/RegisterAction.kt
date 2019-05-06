package com.example.android_bleed.authentication.domain

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.models.User
import com.example.android_bleed.data.repositories.UserRepository
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.flowsteps.UserAction

class RegisterAction : UserAction.UserApplicationAction() {
    override fun execute(application: Application): LiveData<FlowResource> {

        val data = MutableLiveData<FlowResource>()
        val thread = Thread(Runnable {

            val repository = UserRepository(application)
            val user = dataBundle.getParcelable<User>(User.EXTRA_USER)

            if (user == null) {
                data.postValue(FlowResource.FailResource("A user was not provided"))
                return@Runnable
            }

            repository.registerUser(user = user)

            val registerResource = RegisterFlowResource(FlowResource.Status.COMPLETED)
            registerResource.bundle.putParcelable(User.EXTRA_USER, user)

            data.postValue(registerResource)

        })
        thread.start()
        return data
    }

    class RegisterFlowResource(status: Status) : FlowResource(status) {

    }




}