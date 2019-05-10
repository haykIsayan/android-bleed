package com.example.android_bleed.main

import android.app.Application
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.note.NoteListLegend
import com.example.android_bleed.reminder.ReminderListLegend

class MainLegend (application: Application) : AndroidLegend(application) {
    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRootStep(FlowVector().launchFlow(NoteListLegend::class))

            .addFlowVector(ACTION_OPEN_NOTE_LIST, FlowVector().launchFlow(NoteListLegend::class))

            .addFlowVector(ACTION_OPEN_REMINDER_LIST, FlowVector().launchFlow(ReminderListLegend::class))
    }

    companion object {
        const val ACTION_OPEN_NOTE_LIST = "My Notes"
        const val ACTION_OPEN_REMINDER_LIST = "My Reminders"
    }
}