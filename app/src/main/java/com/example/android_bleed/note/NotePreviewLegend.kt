package com.example.android_bleed.note

import android.app.Application
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.note.domain.DeleteNoteAction
import com.example.android_bleed.note.view.NotePreviewDialogFragment

class NotePreviewLegend(application: Application) : AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .startWith(FlowVector().openDialog(NotePreviewDialogFragment::class))

            .addFlowVector(ACTION_DELETE_NOTE, FlowVector().execute(DeleteNoteAction()).dismissDialog(NotePreviewDialogFragment::class).startLegend(NoteListLegend::class))
    }

    companion object {
        const val ACTION_DELETE_NOTE = "Action.Delete.Note"
    }
}