package com.example.android_bleed.flow.flowsteps

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.flow.FlowResource

abstract class UserAction: FlowStep() {

    abstract class UserApplicationAction : UserAction() {
        final override fun execute(): LiveData<FlowResource> {
            return MutableLiveData<FlowResource>()
        }

        abstract fun execute(application: Application): LiveData<FlowResource>
    }

}