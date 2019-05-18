package com.example.android_bleed.editing

import android.app.Application
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.legends.AndroidLegend

class CreateLegend(application: Application) : AndroidLegend(application){

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .addFlowVector(ACTION_CREATE_NOTE, FlowVector().startLegend(CreateNoteLegend::class))
            .addFlowVector(ACTION_CREATE_REMINDER, FlowVector().startLegend(CreateReminderLegend::class))
    }

    companion object {
        const val ACTION_CREATE_NOTE = R.id.menu_note_list.toString()
        const val ACTION_CREATE_REMINDER = R.id.menu_reminder_list.toString()
    }

}