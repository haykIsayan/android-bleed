package com.example.android_bleed.editing

import android.app.Application
import com.example.android_bleed.editing.domain.note.EditNoteAction
import com.example.android_bleed.editing.domain.note.SaveNoteAction
import com.example.android_bleed.editing.view.CreateNoteFragment
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.editing.view.EditingActivity
import com.example.android_bleed.main.MainLegend
import com.example.android_bleed.utilities.SlideAnimation

class CreateNoteLegend (application: Application) : AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRoot(EditingActivity::class, SlideAnimation())

            .startWith(FlowVector().transitionTo(CreateNoteFragment::class, false))

            .addFlowVector(
                ACTION_SAVE_NOTE, FlowVector()
                    .execute(SaveNoteAction())
                    .launchFlow(MainLegend::class)
            )
            .addFlowVector(ACTION_EDIT_NOTE, FlowVector()
                .execute(EditNoteAction())
                .launchFlow(MainLegend::class))
    }

    companion object {
        const val ACTION_SAVE_NOTE = "Action.Save.Note"
        const val ACTION_EDIT_NOTE = "Action.Edit.Note"
    }
}