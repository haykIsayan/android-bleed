package com.example.android_bleed.flow.flowsteps

import androidx.annotation.TransitionRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.flow.FlowResource
import kotlin.reflect.KClass

class FragmentDestination<F : Fragment> (val fragmentKlass: KClass<F>,
                                         @TransitionRes val enterAnimationId: Int = -1,
                                         val addToBackStack: Boolean = true) : FlowStep() {

    override fun execute(): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()

        val userTransitionResource = FlowResource.FragmentTransitionResource(
            fragmentKlass = fragmentKlass
            , enterAnimationId = enterAnimationId,
            addToBackStack = addToBackStack
        )

        userTransitionResource.bundle = dataBundle

        data.postValue(userTransitionResource)
        return data
    }

}