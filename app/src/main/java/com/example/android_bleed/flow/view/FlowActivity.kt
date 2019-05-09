package com.example.android_bleed.flow.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.android_bleed.R
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.flowsteps.FlowLauncher
import com.example.android_bleed.flow.flowsteps.fragment.CustomAnimation
import java.lang.Exception
import java.lang.IllegalArgumentException
import kotlin.reflect.full.primaryConstructor

abstract class FlowActivity : AppCompatActivity(), Observer<FlowResource> {

    private lateinit var mCurrentFlow: AndroidFlow


    private val mFlowMap = mutableMapOf<String, AndroidFlow>()

    private val mFlowData = MediatorLiveData<FlowResource>()

    private var mFragmentContainerId: Int = -1


    protected abstract fun getFragmentContainerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legends)
        this.mFragmentContainerId = getFragmentContainerId()

        this.mFlowData.observe(this, this)
    }

    fun registerFlow(flow: AndroidFlow) {
        this.mFlowMap[flow::class.java.name] = flow
        this.mFlowData.apply {
            addSource(flow.getFlowData()) {
                this.value = it
            }
        }
    }

    fun launchFlow(flow: AndroidFlow, bundle: Bundle = Bundle()) {
        flow.launch(bundle = bundle)
    }

    fun executeFlow(flow: AndroidFlow, vectorTag: String, bundle: Bundle = Bundle()) {
        flow.execute(vectorTag, bundle)
    }

    fun getFlowData(): LiveData<FlowResource> = /*mCurrentFlow.getFlowData()*/ mFlowData

    fun getFlowByName(flowName: String) = mFlowMap[flowName]

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
            is FlowResource.FragmentTransitionResource<*> -> executeFragmentTransition(flowResource)
            is FlowResource.ActivityTransitionResource<*> -> executeActivityTransition(flowResource)
            is FlowResource.FragmentPopResource<*> -> executeFragmentPop(flowResource)
            is FlowLauncher.FlowLauncherResource<*> -> executeFlowLaunch(flowResource)
            else -> {
                if (flowResource.status == FlowResource.Status.COMPLETED) {
                    notifyFlowStepCompleted(flowResource.bundle, flowResource.flowName)
                }
            }
        }
    }

    /**
     * Handle Flow Step Completed
     */

    fun notifyFlowStepCompleted(bundle: Bundle, flowTag: String) {
        this.mFlowMap[flowTag]?.notifyFlowStepCompleted(bundle)
    }

    /**
     * Handle Activity Destination in a Flow
     */

    private fun executeActivityTransition(activityTransitionResource: FlowResource.ActivityTransitionResource<*>) {
        val intent = Intent(this, activityTransitionResource.activityKlass.java)
        intent.putExtras(activityTransitionResource.bundle)
        startActivity(intent)
    }

    /**
     * Handle Fragment Destination in a Flow
     */

    // TODO remove the need for illegal Argument Exception

    @SuppressLint("WrongConstant")
    private fun executeFragmentTransition(fragmentTransitionResource: FlowResource.FragmentTransitionResource<*>) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // ANIMATION CONTROL
        val fragmentAnimation = fragmentTransitionResource.fragmentAnimation
        fragmentAnimation?.apply {
            when (fragmentAnimation) {
                is CustomAnimation -> {
                    fragmentTransaction.setCustomAnimations(fragmentAnimation.enterAnimation,
                        fragmentAnimation.exitAnimation,
                        fragmentAnimation.popEnterAnimation,
                        fragmentAnimation.popExitAnimation)
                }
            }
        }

        if (fragmentTransitionResource.enterAnimationId != -1) {
            try {
                fragmentTransaction.setTransition(fragmentTransitionResource.enterAnimationId)
            } catch (e: Exception) {
                throw IllegalArgumentException("FragmentDestination enterAnimationId must be a FragmentTransaction Animation")
            }
        }

        val fragment = fragmentTransitionResource.fragmentKlass.primaryConstructor?.call() ?: return
        val bundle = fragmentTransitionResource.bundle
        bundle.putString("TAG", fragmentTransitionResource.flowName)
        // ADD FLOW STEP ARGUMENT
        fragment.arguments = bundle
        // BACK STACK CONTROL
        fragmentTransaction.addToBackStack(
            if (fragmentTransitionResource.addToBackStack) {
                fragmentTransitionResource.fragmentKlass.java.name
            } else null
        )




        fragmentTransaction.replace(mFragmentContainerId, fragment).commit()

    }

    private fun executeFragmentPop(fragmentPopResource: FlowResource.FragmentPopResource<*>) {
        val fragmentManager = supportFragmentManager
        if (fragmentPopResource.fragmentKlass != null) {
//            fragmentManager.popBackStack(fragmentPopResource.fragmentKlass.java.name)
        }
        fragmentManager.popBackStack()
        notifyFlowStepCompleted(fragmentPopResource.bundle, fragmentPopResource.flowName)
    }

    private fun executeFlowLaunch(flowLauncherResource: FlowLauncher.FlowLauncherResource<*>) {
        val flow = flowLauncherResource.flowKlass.constructors.first().call(application)
        registerFlow(flow = flow)
        launchFlow(flow = flow)
    }
}
