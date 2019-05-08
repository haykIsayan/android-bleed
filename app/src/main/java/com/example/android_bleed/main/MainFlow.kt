package com.example.android_bleed.main

import android.app.Application
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.note.NoteListFlow
import com.example.android_bleed.reminder.ReminderListFlow

class MainFlow (application: Application) : AndroidFlow(application) {
    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRootStep(FlowVector().launchFlow(NoteListFlow::class))

            .addFlowVector(ACTION_OPEN_NOTE_LIST, FlowVector().launchFlow(NoteListFlow::class))

            .addFlowVector(ACTION_OPEN_REMINDER_LIST, FlowVector().launchFlow(ReminderListFlow::class))
    }

    companion object {
        const val ACTION_OPEN_NOTE_LIST = "My Notes"
        const val ACTION_OPEN_REMINDER_LIST = "My Reminders"
    }
}