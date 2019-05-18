package com.example.android_bleed.main

import android.app.Application
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.note.NoteListLegend
import com.example.android_bleed.note.domain.GetNoteListAction
import com.example.android_bleed.note.view.NoteListFragment
import com.example.android_bleed.reminder.domain.GetReminderListAction
import com.example.android_bleed.reminder.view.ReminderListFragment
import com.example.android_bleed.settings.SettingsFragment
import com.example.android_bleed.utilities.SlideLeftAnimation
import com.example.android_bleed.utilities.SlideRightAnimation

class MainLegend (application: Application) : AndroidLegend(application) {
    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRoot(MainActivity::class, SlideRightAnimation())
            .startWith(FlowVector().startLegend(NoteListLegend::class))

            .addFlowVector(
                ACTION_OPEN_NOTE_LIST,
                FlowVector()
                    .transitionTo(NoteListFragment::class, false)
                    .execute(GetNoteListAction())
            )

            .addFlowVector(
                ACTION_OPEN_REMINDER_LIST,
                FlowVector()
                    .transitionTo(ReminderListFragment::class, false)
                    .execute(GetReminderListAction())
            )

            .addFlowVector(
                ACTION_OPEN_PROFILE,
                FlowVector()
                    .transitionTo(SettingsFragment::class, false)
                    .execute(GetReminderListAction())
            )
    }

    companion object {
        const val ACTION_OPEN_NOTE_LIST = "My Notes"
        const val ACTION_OPEN_REMINDER_LIST = "My Reminders"
        const val ACTION_OPEN_PROFILE = "Profile"
    }
}