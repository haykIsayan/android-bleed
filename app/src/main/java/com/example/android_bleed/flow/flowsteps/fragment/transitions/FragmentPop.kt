package com.example.android_bleed.flow.flowsteps.fragment.transitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.flowsteps.FlowStep
import com.example.android_bleed.flow.view.FlowFragment
import kotlin.reflect.KClass

class FragmentPop<F : FlowFragment> (val fragmentKlass : KClass<F>? = null) : FlowStep() {
    override fun execute(): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()
        data.postValue(FlowResource.FragmentPopResource(fragmentKlass))
        return data
    }
}