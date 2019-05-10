package com.example.android_bleed.android_legends.flowsteps.fragment.transitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.android_legends.FlowResource
import com.example.android_bleed.android_legends.flowsteps.FlowStep
import com.example.android_bleed.android_legends.view.LegendsFragment
import kotlin.reflect.KClass

class FragmentPop<F : LegendsFragment> (val fragmentKlass : KClass<F>? = null) : FlowStep() {
    override fun execute(): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()
        data.postValue(FlowResource.FragmentPopResource(fragmentKlass))
        return data
    }
}