package com.example.android_bleed.android_legends.flowsteps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.android_legends.view.LegendsActivity
import com.example.android_bleed.android_legends.FlowResource
import com.example.android_bleed.android_legends.flowsteps.fragment.CustomAnimation
import kotlin.reflect.KClass

class ActivityDestination <A: LegendsActivity>  (val activityKlass: KClass<A>, val customAnimation: CustomAnimation? = null ) : FlowStep() {
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