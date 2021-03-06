package com.example.android_bleed.reminder

import android.app.Application
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.editing.CreateReminderLegend
import com.example.android_bleed.reminder.domain.GetReminderListAction
import com.example.android_bleed.reminder.view.ReminderListFragment

class ReminderListLegend(application: Application) : AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .startWith(FlowVector().transitionTo(ReminderListFragment::class).execute(GetReminderListAction()))

            .addFlowVector("Create NEW Reminder", FlowVector().startLegend(CreateReminderLegend::class))
    }
}