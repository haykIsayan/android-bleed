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
        (activity as FlowActivity).notifyFlowStepCompleted(bundle)

    }

    fun launchFlow(flow: AndroidFlow, bundle: Bundle = Bundle()) {
        (activity as FlowActivity).launchFlow(flow)
    }

    fun executeFlow(vectorTag: String, bundle: Bundle = Bundle()) {
        (activity as FlowActivity).executeFlow(vectorTag, bundle)
    }

    fun getFlowData() = (activity as FlowActivity).getFlowData()

    abstract fun getLayoutResource(): Int

}
