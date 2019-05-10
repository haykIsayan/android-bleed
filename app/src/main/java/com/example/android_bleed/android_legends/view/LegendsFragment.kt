package com.example.android_bleed.android_legends.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bleed.android_legends.AndroidLegend
import kotlin.reflect.KClass


abstract class LegendsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments ?: Bundle()
        (activity as LegendsActivity).notifyFlowStepCompleted(bundle, bundle.getString("TAG")!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(getLayoutResource(), container, false)
    }

    fun <L : AndroidLegend> executeFlow(flowKlass: KClass<L>,
                                        vectorTag: String = AndroidLegend.ACTION_LAUNCH_FLOW,
                                        bundle: Bundle = Bundle()) {

        (activity as LegendsActivity).executeFlow(flowKlass, vectorTag, bundle)
    }

    fun getFlowData() = (activity as LegendsActivity).getFlowData()

    fun getFlowByName(flowName: String) = (activity as LegendsActivity).getFlowByName(flowName)
    abstract fun getLayoutResource(): Int

}
