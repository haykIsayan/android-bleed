package com.example.android_bleed.flow.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bleed.flow.AndroidFlow
import kotlin.reflect.KClass


abstract class FlowFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments ?: Bundle()
        (activity as FlowActivity).notifyFlowStepCompleted(bundle, bundle.getString("TAG")!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(getLayoutResource(), container, false)
    }

    fun <L : AndroidFlow> executeFlow(flowKlass: KClass<L>,
                                      vectorTag: String = AndroidFlow.ACTION_LAUNCH_FLOW,
                                      bundle: Bundle = Bundle()) {

        (activity as FlowActivity).executeFlow(flowKlass, vectorTag, bundle)
    }

    fun getFlowData() = (activity as FlowActivity).getFlowData()

    fun getFlowByName(flowName: String) = (activity as FlowActivity).getFlowByName(flowName)
    abstract fun getLayoutResource(): Int

}
