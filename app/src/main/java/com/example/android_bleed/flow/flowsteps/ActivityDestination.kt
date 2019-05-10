package com.example.android_bleed.flow.flowsteps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.flow.view.FlowActivity
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.flowsteps.fragment.CustomAnimation
import kotlin.reflect.KClass

class ActivityDestination <A: FlowActivity>  (val activityKlass: KClass<A>, val customAnimation: CustomAnimation? = null ) : FlowStep() {
    override fun execute(): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()
        val activityTransitionResource = FlowResource.ActivityTransitionResource(
            activityKlass = activityKlass,
            customAnimation = customAnimation
        )
        activityTransitionResource.bundle = dataBundle
        data.postValue(activityTransitionResource)
        return data
    }
}