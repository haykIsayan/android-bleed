package com.example.android_bleed.editing

import android.app.Application
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.editing.domain.reminder.SaveReminderAction
import com.example.android_bleed.editing.view.EditingActivity
import com.example.android_bleed.editing.view.CreateReminderFragment
import com.example.android_bleed.main.MainActivity
import com.example.android_bleed.reminder.domain.GetReminderListAction
import com.example.android_bleed.reminder.view.ReminderListFragment

class CreateReminderLegend(application: Application) : AndroidLegend(application) {
    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph().setRoot(EditingActivity::class)

            .startWith(FlowVector().transitionTo(CreateReminderFragment::class))

            .addFlowVector(
                ACTION_SAVE_REMINDER, FlowVector()

                    .execute(SaveReminderAction())
                    .startLegend(FlowGraph()
                        .setRoot(MainActivity::class)
                        .startWith(FlowVector()
                            .transitionTo(ReminderListFragment::class)
                            .execute(GetReminderListAction())))
            )
    }

    companion object {
        const val ACTION_SAVE_REMINDER = "Action.Save.Reminder"
    }
}