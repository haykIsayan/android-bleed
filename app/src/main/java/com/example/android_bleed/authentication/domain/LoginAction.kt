package com.example.android_bleed.authentication.domain

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.models.User
import com.example.android_bleed.data.repositories.UserRepository
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.flowsteps.UserAction

class LoginAction : UserAction.UserApplicationAction() {

    override fun execute(application: Application): LiveData<FlowResource> {

        val data = MutableLiveData<FlowResource>()
        val thread = Thread(Runnable {

            val userName = dataBundle.getString(User.EXTRA_USERNAME)
            val password = dataBundle.getString(User.EXTRA_PASSWORD)

            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                data.postValue(FlowResource.FailResource("Please provide a username and a password"))
                return@Runnable
            }

            val user = UserRepository(application).getUserByUsername(userName!!)

            user?.apply {
                val resource = FlowResource(FlowResource.Status.COMPLETED)
                resource.bundle.putParcelable(User.EXTRA_USER, user)
                data.postValue(resource)
                return@Runnable
            }
            data.postValue(FlowResource.FailResource("No user found with username \"$userName\""))
        })
        thread.start()
        return data
    }

}