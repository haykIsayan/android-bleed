package com.example.android_bleed.editing

import android.app.Application
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.editing.domain.reminder.SaveReminderAction
import com.example.android_bleed.editing.view.EditingActivity
import com.example.android_bleed.main.MainLegend
import com.example.android_bleed.editing.view.CreateReminderFragment

class CreateReminderLegend(application: Application) : AndroidLegend(application) {
    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph().setRoot(EditingActivity::class)
            .startWith(FlowVector().transitionTo(CreateReminderFragment::class))
            .addFlowVector(ACTION_SAVE_REMINDER, FlowVector().execute(SaveReminderAction()).launchFlow(MainLegend::class))
    }

    companion object {
        const val ACTION_SAVE_REMINDER = "Action.Save.Reminder"
    }
}