package com.example.android_bleed.android_legends.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.android_legends.FlowResource
import com.example.android_bleed.android_legends.flowsteps.ActivityDestination
import com.example.android_bleed.android_legends.flowsteps.FlowLauncher
import com.example.android_bleed.android_legends.flowsteps.fragment.CustomAnimation
import java.lang.Exception
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

abstract class LegendsActivity : AppCompatActivity(), Observer<FlowResource> {

    private val mFlowMap = mutableMapOf<String, AndroidLegend>()

    private val mFlowData = MediatorLiveData<FlowResource>()

    private var mFragmentContainerId: Int = -1

    companion object {
        const val LAUNCHER_LEGEND = "Launcher.Flow"
    }

    protected abstract fun getFragmentContainerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legends)
        this.mFragmentContainerId = getFragmentContainerId()

        registerLauncherLegend()

        this.mFlowData.observe(this, this)
    }

    /**
     * UTILITY FUNCTIONS
     */

    // todo/ code improvements

    private fun registerLauncherLegend() {
        val bundle = intent.extras ?: return
        bundle.getSerializable(LAUNCHER_LEGEND)?.apply {
            when (this) {
                is AndroidLegend -> {
                    executeFlow(this.javaClass.kotlin)
                }
            }
        }
    }

    private fun <L : AndroidLegend> initAndroidLegend(flowKlass: KClass<L>) = flowKlass.constructors.first().call(application)

    private fun <L : AndroidLegend> registerFlow(flowKlass: KClass<L>): AndroidLegend {
        val flowName = flowKlass.java.name

        var flow = mFlowMap[flowName]
        flow.apply {

            flow = initAndroidLegend(flowKlass)

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
     * UTILITY FUNCTIONS
     */

    /**
     * PRIMARY CONTROLLER FUNCTIONS
     */


    /**
     * LAUNCH A BRAND NEW FLOW
     */

    fun <L : AndroidLegend> launchLegend(legendKlass: KClass<L>) {
        val legend = initAndroidLegend(legendKlass)
        val legendsDestination = legend.getRoot()
        legendsDestination?.apply {
            when (this) {
                is ActivityDestination<*> -> {
                    val resource =
                        FlowResource.ActivityTransitionResource(this.getActivityKlass(), this.customAnimation)
                    val bundle = Bundle()
                    bundle.putSerializable(LAUNCHER_LEGEND, legend)
                    resource.bundle = bundle
                    executeActivityTransition(resource)
                }
            }
            return
        }
        executeFlow(legendKlass)
    }

    fun <L : AndroidLegend> executeFlow(flowKlass: KClass<L>, vectorTag: String = AndroidLegend.ACTION_LAUNCH_FLOW, bundle: Bundle = Bundle()) {
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
            is FlowLauncher.FlowLauncherResource<*> -> launchLegend(legendKlass = flowResource.flowKlass /*,bundle = flowResource.bundle*/)
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
