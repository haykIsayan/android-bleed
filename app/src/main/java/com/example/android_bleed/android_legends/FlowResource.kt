package com.example.android_bleed.android_legends

import android.app.Application
import android.os.Bundle
import androidx.annotation.TransitionRes
import androidx.fragment.app.Fragment
import com.example.android_bleed.android_legends.flowsteps.fragment.CustomAnimation
import com.example.android_bleed.android_legends.flowsteps.fragment.FragmentAnimation
import com.example.android_bleed.android_legends.view.LegendsActivity
import com.example.android_bleed.android_legends.view.LegendsFragment
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

    data class ActivityTransitionResource<A : LegendsActivity> (val activityKlass: KClass<A>, val customAnimation: CustomAnimation? = null): FlowResource(Status.COMPLETED)

    data class FragmentPopResource<F : LegendsFragment> (val fragmentKlass : KClass<F>? = null): FlowResource(Status.COMPLETED)

    data class FailResource(val failMessage: String = "") : FlowResource(Status.FAILED)

    enum class Status { PENDING, COMPLETED, FAILED }

}