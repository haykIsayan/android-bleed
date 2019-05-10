package com.example.android_bleed.android_legends.flowsteps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.android_legends.FlowResource
import kotlin.reflect.KClass

class FlowLauncher<F : AndroidLegend>(val flowKlass: KClass<F>) : FlowStep() {

    override fun execute(): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()
        data.postValue(FlowLauncherResource(flowKlass))
        return data
    }

    data class FlowLauncherResource<F : AndroidLegend> (val flowKlass: KClass<F>): FlowResource()

}