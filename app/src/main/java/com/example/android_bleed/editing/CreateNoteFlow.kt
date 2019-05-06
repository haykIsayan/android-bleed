package com.example.android_bleed.editing

import android.app.Application
import com.example.android_bleed.editing.domain.SaveNoteAction
import com.example.android_bleed.editing.view.CreateNoteFragment
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.main.domain.GetNoteListAction

class CreateNoteFlow (application: Application) : AndroidFlow(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRootStep(FlowVector().transitionTo(CreateNoteFragment::class))
            .addFlowVector(
                ACTION_SAVE_NOTE, FlowVector()
                    .execute(SaveNoteAction())
                    .popBack(CreateNoteFragment::class)
                    .execute(GetNoteListAction())
            )
    }

    companion object {
        const val ACTION_SAVE_NOTE = "Action.Save.Note"
    }
}