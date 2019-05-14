package com.example.android_bleed.android_legends.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bleed.android_legends.legends.AndroidLegend
import kotlin.reflect.KClass


abstract class LegendsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments ?: Bundle()
        (activity as LegendsActivity).notifyFlowStepCompleted(bundle, bundle.getString(LegendsActivity.FRAGMENT_TRANSITION_BUNDLE)!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(getLayoutResource(), container, false)
    }

    fun <L : AndroidLegend> startLegend(legendKlass: KClass<L>) {
        (activity as LegendsActivity).startLegend(legendKlass)
    }

    fun <L : AndroidLegend> executeLegend(flowKlass: KClass<L>,
                                                                                            vectorTag: String = AndroidLegend.ACTION_LAUNCH_FLOW,
                                                                                            bundle: Bundle = Bundle()) {

        (activity as LegendsActivity).executeLegend(flowKlass, vectorTag, bundle)
    }

    fun getLegendData() = (activity as LegendsActivity).getLegendData()

    abstract fun getLayoutResource(): Int

}
