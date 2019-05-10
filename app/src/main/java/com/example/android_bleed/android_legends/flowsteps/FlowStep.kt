package com.example.android_bleed.android_legends.flowsteps

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.android_bleed.android_legends.FlowResource

abstract class FlowStep {

    private val flowStepData = MediatorLiveData<FlowResource>()

    private val inputKeyList = mutableListOf<String>()

    lateinit var dataBundle: Bundle


//    fun invoke(): LiveData<FlowResource> {
//
//        flowStepData.apply {
//            val newData = execute()
//            addSource(newData) { newValue ->
//                postValue(newValue)
////                removeSource(newData)
//            }
//        }
//        return flowStepData
//    }




    abstract fun execute(): LiveData<FlowResource>

}