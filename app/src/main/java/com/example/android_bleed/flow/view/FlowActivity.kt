package com.example.android_bleed.flow.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.android_bleed.R
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.flow.FlowResource
import java.lang.Exception
import java.lang.IllegalArgumentException
import kotlin.reflect.full.primaryConstructor

abstract class FlowActivity : AppCompatActivity(), Observer<FlowResource> {

    private lateinit var mCurrentFlow: AndroidFlow
    private val mFlowData = MediatorLiveData<FlowResource>()

    private var mFragmentContainerId: Int = -1


    protected abstract fun getFragmentContainerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legends)
        this.mFragmentContainerId = getFragmentContainerId()


        // todo trial
        this.mFlowData.observe(this, this)

    }

    fun launchFlow(flow: AndroidFlow) {
        this.mCurrentFlow = flow
//        this.mCurrentFlow.getFlowData().observe(this, this)
//        this.mCurrentFlow.launch()
//

        // todo trial

        this.mFlowData.apply {
            addSource(mCurrentFlow.getFlowData()) {
                this.value = it
            }
        }
        this.mCurrentFlow.launch()
    }

    fun executeFlow(vectorTag: String, bundle: Bundle = Bundle()) {
        this.mCurrentFlow.execute(vectorTag, bundle)
    }

    fun getFlowData() = /*mCurrentFlow.getFlowData()*/ mFlowData

    /**
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * ////////////////////////////////////// ON FLOW RESULT CONTROL ///////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    final override fun onChanged(flowResource: FlowResource?) {
        flowResource ?: return
        when (flowResource) {
            is FlowResource.FragmentTransitionResource<*> -> onFragmentTransition(flowResource)
            is FlowResource.ActivityTransitionResource<*> -> onActivityTransition(flowResource)
            is FlowResource.FragmentPopResource<*> -> onFragmentPop(flowResource)
            else -> {
                if (flowResource.status == FlowResource.Status.COMPLETED) {
                    notifyFlowStepCompleted(flowResource.bundle)
                }
            }
        }
    }

    /**
     * Handle Flow Step Completed
     */
    fun notifyFlowStepCompleted(bundle: Bundle) {
        mCurrentFlow.notifyFlowStepCompleted(bundle)
    }

    /**
     * Handle Activity Destination in a Flow
     */

    private fun onActivityTransition(activityTransitionResource: FlowResource.ActivityTransitionResource<*>) {
        val intent = Intent(this, activityTransitionResource.activityKlass.java)
        intent.putExtras(activityTransitionResource.bundle)
        startActivity(intent)
    }

    /**
     * Handle Fragment Destination in a Flow
     */

    // TODO remove the need for illegal Argument Exception

    @SuppressLint("WrongConstant")
    private fun onFragmentTransition(fragmentTransitionResource: FlowResource.FragmentTransitionResource<*>) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (fragmentTransitionResource.enterAnimationId != -1) {
            try {
                fragmentTransaction.setTransition(fragmentTransitionResource.enterAnimationId)
            } catch (e: Exception) {
                throw IllegalArgumentException("FragmentDestination enterAnimationId must be a FragmentTransaction Animation")
            }
        }

        val fragment = fragmentTransitionResource.fragmentKlass.primaryConstructor?.call() ?: return
        fragment.arguments = fragmentTransitionResource.bundle
        // back stack control
        fragmentTransaction.addToBackStack(
            if (fragmentTransitionResource.addToBackStack) {
                fragmentTransitionResource.fragmentKlass.java.name
            } else null
        )
        fragmentTransaction.replace(mFragmentContainerId, fragment).commit()

    }

    private fun onFragmentPop(fragmentPopResource: FlowResource.FragmentPopResource<*>) {
        val fragmentManager = supportFragmentManager
        if (fragmentPopResource.fragmentKlass != null) {
//            fragmentManager.popBackStack(fragmentPopResource.fragmentKlass.java.name)
        }
        fragmentManager.popBackStack()
    }
}
