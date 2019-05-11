package com.example.android_bleed.android_legends

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.Ignore
import com.example.android_bleed.android_legends.flowsteps.*
import com.example.android_bleed.android_legends.flowsteps.fragment.CustomAnimation
import com.example.android_bleed.android_legends.flowsteps.fragment.FragmentAnimation
import com.example.android_bleed.android_legends.flowsteps.fragment.transitions.FragmentDestination
import com.example.android_bleed.android_legends.flowsteps.fragment.transitions.FragmentPop
import com.example.android_bleed.android_legends.view.LegendsActivity
import com.example.android_bleed.android_legends.view.LegendsFragment
import java.io.Serializable
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

abstract class AndroidLegend(@Transient private val mApplication: Application) : Serializable{

    private val mFlowName = this::class.java.name
    @Transient
    private lateinit var mFlowGraph: FlowGraph
    @Transient
    private lateinit var mCurrentVectorIterator: FlowVectorIterator

    @Transient
    private var mFlowData = MediatorLiveData<FlowResource>()


    companion object {
        const val ACTION_LAUNCH_ROOT = "Action.Launch.Root"
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

    /**
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * ////////////////////////////////////////// UTILITY FUNCTIONS ////////////////////////////////////////////////////
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

    /**
     * ACCESSIBILITY FUNCTIONS
     */

    fun getFlowData(): LiveData<FlowResource> = mFlowData

    fun getRoot() = mFlowGraph.getRoot()


    /**
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * //////////////////////////////////////// Utility Classes ////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    protected class FlowGraph {

        private val mFlowVectorMap = mutableMapOf<String, FlowVector>()

        fun setRootStep(flowVector: FlowVector) = apply {
            mFlowVectorMap[AndroidLegend.ACTION_LAUNCH_FLOW] = flowVector
        }

        fun <L : LegendsActivity> setRoot(activityKlass : KClass<L>) = apply {
            mFlowVectorMap[ACTION_LAUNCH_ROOT] = FlowVector().startActivity(activityKlass = activityKlass)
        }

        fun addFlowVector(stepTag: String, flowVector: FlowVector) = apply { mFlowVectorMap[stepTag] = flowVector }


        fun getRoot (): KClass<out LegendsActivity>? {

            val vector = mFlowVectorMap[ACTION_LAUNCH_ROOT]?: return null
//                ?: throw IllegalArgumentException("WTF")

            val activityDestination = vector.getStepList().first()
            when (activityDestination) {
                is ActivityDestination<*> -> {
                    return activityDestination.getActivityKlass()
                }
            }
            return null
        }


        fun getFlowVector(stepTag: String) = mFlowVectorMap[stepTag]

    }

    class FlowVector {
        private val mFlowStepList = mutableListOf<FlowStep>()


        fun <A : LegendsActivity> startActivity(activityKlass: KClass<A>, customAnimation: CustomAnimation? = null) = apply {
            mFlowStepList.add(ActivityDestination(activityKlass = activityKlass, customAnimation = customAnimation))
        }

        fun <A: LegendsActivity> startActivityForResult(activityKlass: KClass<A>) = apply {

        }

        fun <F : Fragment> transitionTo(fragmentKlass: KClass<F>, addToBackStack : Boolean = true,
                                        fragmentAnimation: FragmentAnimation? = null) =
            apply {
                mFlowStepList.add(
                    FragmentDestination(
                        fragmentKlass = fragmentKlass,
                        addToBackStack = addToBackStack,
                        fragmentAnimation = fragmentAnimation
                    )
                )
            }

        fun execute(userAction: UserAction) =
            apply {
                mFlowStepList.add(userAction)
            }

        fun <F : LegendsFragment> popBack(fragmentKlass: KClass<F>? = null) =
            apply {
                this.mFlowStepList.add(
                    FragmentPop(
                        fragmentKlass
                    )
                )
            }

        fun <F : AndroidLegend> launchFlow(flowKlass: KClass<F>) =
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

    data class LegendLauncher<A : AndroidLegend> (val androidLegendKlass: KClass<A>): Serializable

}