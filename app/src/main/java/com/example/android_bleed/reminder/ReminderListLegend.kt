package com.example.android_bleed.reminder

import android.app.Application
import com.example.android_bleed.android_legends.AndroidLegend

class ReminderListLegend(application: Application) : AndroidLegend(application) {


    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph().setRootStep(FlowVector().transitionTo(ReminderListFragment::class))
    }
}