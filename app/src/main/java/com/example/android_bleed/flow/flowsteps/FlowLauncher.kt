package com.example.android_bleed.flow.flowsteps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.flow.FlowResource
import kotlin.reflect.KClass

class FlowLauncher<F : AndroidFlow>(val flowKlass: KClass<F>) : FlowStep() {

    override fun execute(): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()
        data.postValue(FlowLauncherResource(flowKlass))
        return data
    }

    data class FlowLauncherResource<F : AndroidFlow> (val flowKlass: KClass<F>): FlowResource()

}