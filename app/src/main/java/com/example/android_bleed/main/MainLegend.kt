package com.example.android_bleed.main

import android.app.Application
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.note.NoteListLegend
import com.example.android_bleed.note.domain.GetNoteListAction
import com.example.android_bleed.note.view.NoteListFragment
import com.example.android_bleed.reminder.ReminderListFragment
import com.example.android_bleed.utilities.SlideAnimation

class MainLegend (application: Application) : AndroidLegend(application) {
    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRoot(MainActivity::class, SlideAnimation())
            .startWith(FlowVector().launchFlow(NoteListLegend::class))

            .addFlowVector(ACTION_OPEN_NOTE_LIST, FlowVector().transitionTo(NoteListFragment::class).execute(GetNoteListAction()))

            .addFlowVector(ACTION_OPEN_REMINDER_LIST, FlowVector().transitionTo(ReminderListFragment::class).execute(GetNoteListAction()))
    }

    companion object {
        const val ACTION_OPEN_NOTE_LIST = "My Notes"
        const val ACTION_OPEN_REMINDER_LIST = "My Reminders"
    }
}