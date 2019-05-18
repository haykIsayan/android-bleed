package com.example.android_bleed.reminder

import android.app.Application
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.editing.view.EditingActivity
import com.example.android_bleed.reminder.view.ReminderPreviewFragment
import com.example.android_bleed.utilities.SlideRightAnimation

class ReminderPreviewLegend(application: Application) : AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRoot(EditingActivity::class, SlideRightAnimation())
            .startWith(FlowVector().transitionTo(ReminderPreviewFragment::class))
    }

}