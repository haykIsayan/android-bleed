package com.example.android_bleed.editing

import android.app.Application
import com.example.android_bleed.editing.domain.note.EditNoteAction
import com.example.android_bleed.editing.domain.note.SaveNoteAction
import com.example.android_bleed.editing.view.CreateNoteFragment
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.editing.view.EditingActivity
import com.example.android_bleed.main.MainActivity
import com.example.android_bleed.main.MainLegend
import com.example.android_bleed.note.domain.GetNoteListAction
import com.example.android_bleed.note.view.NoteListFragment
import com.example.android_bleed.utilities.SlideRightAnimation
import com.example.android_bleed.utilities.SlideUpAnimation

class CreateNoteLegend (application: Application) : AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRoot(EditingActivity::class, SlideRightAnimation())

            .startWith(FlowVector().transitionTo(CreateNoteFragment::class, false))

            .addFlowVector(
                ACTION_SAVE_NOTE, FlowVector()
                    .execute(SaveNoteAction())
                    .startLegend(
                        FlowGraph().setRoot(MainActivity::class).startWith(
                            FlowVector().transitionTo(
                                NoteListFragment::class
                            ).execute(GetNoteListAction())
                        )
                    )
            )
            .addFlowVector(ACTION_EDIT_NOTE, FlowVector()
                .execute(EditNoteAction())
                .startLegend(MainLegend::class))
    }

    companion object {
        const val ACTION_SAVE_NOTE = "Action.Save.Note"
        const val ACTION_EDIT_NOTE = "Action.Edit.Note"
    }
}