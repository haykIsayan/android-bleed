package com.example.android_bleed.flow.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bleed.flow.AndroidFlow


abstract class FlowFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onResume() {
        super.onResume()

        val bundle = arguments ?: Bundle()
        (activity as FlowActivity).notifyFlowStepCompleted(bundle, bundle.getString("TAG")!!)

    }

    fun registerFlow(flow: AndroidFlow) {
        (activity as FlowActivity).registerFlow(flow)
    }

    fun launchFlow(flow: AndroidFlow, bundle: Bundle = Bundle()) {
        (activity as FlowActivity).launchFlow(flow)
    }

    fun executeFlow(flow: AndroidFlow, vectorTag: String, bundle: Bundle = Bundle()) {
        (activity as FlowActivity).executeFlow(flow, vectorTag, bundle)
    }

    fun getFlowData() = (activity as FlowActivity).getFlowData()

    fun getFlowByName(flowName: String) = (activity as FlowActivity).getFlowByName(flowName)
    abstract fun getLayoutResource(): Int

}
