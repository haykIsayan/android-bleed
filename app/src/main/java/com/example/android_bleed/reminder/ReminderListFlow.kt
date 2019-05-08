package com.example.android_bleed.reminder

import android.app.Application
import com.example.android_bleed.flow.AndroidFlow

class ReminderListFlow(application: Application) : AndroidFlow(application) {


    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph().setRootStep(FlowVector().transitionTo(ReminderListFragment::class))
    }
}