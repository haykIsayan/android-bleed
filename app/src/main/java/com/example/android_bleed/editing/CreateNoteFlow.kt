package com.example.android_bleed.editing

import android.app.Application
import com.example.android_bleed.editing.domain.SaveNoteAction
import com.example.android_bleed.editing.view.CreateNoteFragment
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.note.domain.GetNoteListAction

class CreateNoteFlow (application: Application) : AndroidFlow(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRootStep(FlowVector().transitionTo(CreateNoteFragment::class, true))
            .addFlowVector(
                ACTION_SAVE_NOTE, FlowVector()
                    .execute(SaveNoteAction())
                    .popBack(CreateNoteFragment::class)
                    .execute(GetNoteListAction())
            )
            .addFlowVector(ACTION_EDIT_NOTE, FlowVector()
                .execute(EditNoteFlow())
                .popBack(CreateNoteFragment::class)
                .execute(GetNoteListAction()))
    }

    companion object {
        const val ACTION_SAVE_NOTE = "Action.Save.Note"
        const val ACTION_EDIT_NOTE = "Action.Edit.Note"
    }
}