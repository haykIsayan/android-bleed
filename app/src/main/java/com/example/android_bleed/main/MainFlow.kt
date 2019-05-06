package com.example.android_bleed.main

import android.app.Application
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.main.domain.GetNoteListAction
import com.example.android_bleed.main.view.NoteListFragment


class MainFlow(application: Application) : AndroidFlow(application) {

    override fun onCreateFlowGraph(): FlowGraph = FlowGraph().setRootStep(
        FlowVector()
            .transitionTo(NoteListFragment::class, false)
            .execute(GetNoteListAction())
    )


}