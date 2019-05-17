package com.example.android_bleed.note

import android.app.Application
import com.example.android_bleed.editing.CreateNoteLegend
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.note.domain.GetNoteListAction
import com.example.android_bleed.note.view.NoteListFragment


class NoteListLegend(application: Application) : AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph =
        FlowGraph()
            .startWith(FlowVector().transitionTo(NoteListFragment::class, false).execute(GetNoteListAction()))

            .addFlowVector(ACTION_LAUNCH_CREATE_FLOW, FlowVector().startLegend(CreateNoteLegend::class))

    companion object {
        const val ACTION_LAUNCH_CREATE_FLOW = "Action.Launch.Create.Flow"
    }
}