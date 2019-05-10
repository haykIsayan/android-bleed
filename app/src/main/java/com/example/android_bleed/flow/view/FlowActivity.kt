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
import kotlin.reflect.KClass
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

    // todo/ code improvements

    private fun <L : AndroidFlow> registerFlow(flowKlass: KClass<L>): AndroidFlow {
        val flowName = flowKlass.java.name

        var flow = mFlowMap[flowName]
        flow.apply {

            flow = flowKlass.constructors.first().call(application)

            mFlowMap[flowName] = flow!!
            mFlowData.apply {
                addSource(flow!!.getFlowData()) {
                    this.value = it
                }
            }
        }
        return flow!!
    }

    /**
     * PRIMARY CONTROLLER FUNCTIONS
     */

    fun <L : AndroidFlow> executeFlow(flowKlass: KClass<L>, vectorTag: String = AndroidFlow.ACTION_LAUNCH_FLOW, bundle: Bundle = Bundle()) {
        val flow = registerFlow(flowKlass)
        flow.execute(vectorTag, bundle)
    }

    fun getFlowData(): LiveData<FlowResource> = mFlowData

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
            is FlowLauncher.FlowLauncherResource<*> -> executeFlow(flowKlass = flowResource.flowKlass, bundle = flowResource.bundle)
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

        val customAnimation = activityTransitionResource.customAnimation
        // ANIMATION HANDLING
        customAnimation?.apply {
            overridePendingTransition(customAnimation.enterAnimation, customAnimation.exitAnimation)
        }
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
                    if (fragmentAnimation.popEnterAnimation == -1 || fragmentAnimation.popExitAnimation == -1) {
                        fragmentTransaction.setCustomAnimations(fragmentAnimation.enterAnimation, fragmentAnimation.exitAnimation)
                    } else {
                        fragmentTransaction.setCustomAnimations(
                            fragmentAnimation.enterAnimation,
                            fragmentAnimation.exitAnimation,
                            fragmentAnimation.popEnterAnimation,
                            fragmentAnimation.popExitAnimation
                        )
                    }
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

}
