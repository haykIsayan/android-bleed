package com.example.android_bleed.flow

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import com.example.android_bleed.flow.flowsteps.*
import com.example.android_bleed.flow.view.FlowActivity
import com.example.android_bleed.flow.view.FlowFragment
import kotlin.reflect.KClass

abstract class AndroidFlow (val mApplication: Application){

    private val mFlowName = this::class.java.name
    private lateinit var mFlowGraph: FlowGraph
    private lateinit var mCurrentVectorIterator: FlowVectorIterator

    private var mFlowData = MediatorLiveData<FlowResource>()


    companion object {
        const val ACTION_LAUNCH_FLOW = "Action.Launch.Flow" }


    init {
        onCreateFlow()
    }

    private fun onCreateFlow() {
        mFlowGraph = onCreateFlowGraph()
    }

    protected abstract fun onCreateFlowGraph(): FlowGraph

    /**
     * Main Controller Functions
     */

    fun launch(bundle: Bundle = Bundle()) {
        val flowVector = mFlowGraph.getFlowVector(ACTION_LAUNCH_FLOW)?:return
        invokeFlowVector(flowVector, bundle)
    }

    fun execute(vectorTag: String, bundle: Bundle) {
        val flowVector = mFlowGraph.getFlowVector(vectorTag)?:return
        invokeFlowVector(flowVector, bundle)
    }

    fun isCompleted() = mCurrentVectorIterator.getFlowStep() == null

    /**
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * //////////////////////////////////////// UTILITY FUNCTIONS //////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    private fun invokeFlowVector(flowVector: FlowVector, bundle: Bundle) {
        this.mCurrentVectorIterator = FlowVectorIterator(flowVector)
        executeVectorIterator(bundle)
    }

    /**
     * Notify that the last flow step has completed its task and
     * that the iterator must continue on with flow step execution
     */

    fun notifyFlowStepCompleted(bundle: Bundle) {
        executeVectorIterator(bundle)
    }

    private fun executeVectorIterator(bundle: Bundle = Bundle()) {
        val flowStep = mCurrentVectorIterator.getFlowStep()?:return
        flowStep.dataBundle = bundle
        val flowStepData = when(flowStep) {
            is UserAction.UserApplicationAction -> flowStep.execute(mApplication)
            else -> flowStep.execute()
        }
        mFlowData.apply {
            addSource(flowStepData) {
                it.flowName = mFlowName
                value = it
            }
        }
    }

    fun getFlowData() = mFlowData

    /**
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * Utility Classes
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    protected class FlowGraph {

        private val mFlowVectorMap = mutableMapOf<String, FlowVector>()

        fun setRootStep(flowVector: FlowVector) = apply {
            mFlowVectorMap[AndroidFlow.ACTION_LAUNCH_FLOW] = flowVector
        }

        fun addFlowVector(stepTag: String, flowVector: FlowVector) = apply { mFlowVectorMap[stepTag] = flowVector }

        fun getFlowVector(stepTag: String) = mFlowVectorMap[stepTag]

    }

    inner class FlowVector {
        private val mFlowStepList = mutableListOf<FlowStep>()


        fun <A : FlowActivity> startActivity(activityKlass: KClass<A>) = apply {
            mFlowStepList.add(ActivityDestination(activityKlass = activityKlass))
        }

        fun <A: FlowActivity> startActivityForResult(activityKlass: KClass<A>) = apply {

        }

        fun <F : Fragment> transitionTo(fragmentKlass: KClass<F>, addToBackStack : Boolean = true) =
            apply {
                mFlowStepList.add(FragmentDestination(fragmentKlass = fragmentKlass, addToBackStack = addToBackStack))
            }

        fun execute(userAction: UserAction) =
            apply {
                mFlowStepList.add(userAction)
            }

        fun <F : FlowFragment> popBack(fragmentKlass: KClass<F>? = null) =
            apply {
                this.mFlowStepList.add(FragmentPop(fragmentKlass))
            }

        fun <F : AndroidFlow> launchFlow(flowKlass: KClass<F>) =
                apply {
                    this.mFlowStepList.add(FlowLauncher(flowKlass = flowKlass))
                }


        fun getStepList() = this.mFlowStepList
    }

    inner class FlowVectorIterator (private val mFlowVector: FlowVector) {
        private var mFlowStepCounter = -1

        fun getFlowStep() = if (++mFlowStepCounter >= mFlowVector.getStepList().size) {
            null
        } else {
            mFlowVector.getStepList()[mFlowStepCounter]
        }
    }











}