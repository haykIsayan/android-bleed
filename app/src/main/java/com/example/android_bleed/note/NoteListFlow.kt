package com.example.android_bleed.note

import android.app.Application
import com.example.android_bleed.editing.CreateNoteFlow
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.note.domain.GetNoteListAction
import com.example.android_bleed.note.view.NoteListFragment


class NoteListFlow(application: Application) : AndroidFlow(application) {

    override fun onCreateFlowGraph(): FlowGraph =
        FlowGraph()
            .setRootStep(FlowVector().transitionTo(NoteListFragment::class).execute(GetNoteListAction()))

            .addFlowVector(ACTION_LAUNCH_CREATE_FLOW, FlowVector().launchFlow(CreateNoteFlow::class))

    companion object {
        const val ACTION_LAUNCH_CREATE_FLOW = "Action.Launch.Create.Flow"
    }
}