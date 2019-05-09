package com.example.android_bleed.flow

import android.app.Application
import android.os.Bundle
import androidx.annotation.TransitionRes
import androidx.fragment.app.Fragment
import com.example.android_bleed.flow.flowsteps.fragment.FragmentAnimation
import com.example.android_bleed.flow.view.FlowActivity
import com.example.android_bleed.flow.view.FlowFragment
import kotlin.reflect.KClass

open class FlowResource (val status: Status = Status.PENDING,
                         var bundle: Bundle = Bundle(),
                         val application: Application? = null,
                         var flowName: String = ""){

    companion object {
        fun fail(failMessage: String = "") = FailResource(failMessage)
    }

    data class FragmentTransitionResource<F : Fragment> (val fragmentKlass: KClass<F>,
                                                         @TransitionRes val enterAnimationId: Int = -1,
                                                         val addToBackStack: Boolean = true,
                                                         val fragmentAnimation: FragmentAnimation? = null): FlowResource(Status.COMPLETED)

    data class ActivityTransitionResource<A : FlowActivity> (val activityKlass: KClass<A>): FlowResource(Status.COMPLETED)

    data class FragmentPopResource<F : FlowFragment> (val fragmentKlass : KClass<F>? = null): FlowResource(Status.COMPLETED)

    data class FailResource(val failMessage: String = "") : FlowResource(Status.FAILED)

    enum class Status { PENDING, COMPLETED, FAILED }

}