package com.example.android_bleed.note.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.view.LegendsDialogFragment
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.editing.CreateNoteLegend
import com.example.android_bleed.note.NotePreviewLegend

class NotePreviewDialogFragment : LegendsDialogFragment() {

    private lateinit var tvNoteTitle: TextView
    private lateinit var tvNoteText: TextView
    private lateinit var tvNoteDate: TextView
    private lateinit var mNote: Note

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)


        val builder = AlertDialog.Builder(activity!!)

        val inflater = LayoutInflater.from(activity)
        val layout = inflater.inflate(R.layout.layout_note_preview_dialog, null)

        mNote = arguments?.getParcelable(Note.EXTRA_NOTE)?: return Dialog(activity)

        builder
            .setView(layout)
            .setPositiveButton("Edit Note") { dialog, which ->
                startLegend(CreateNoteLegend::class, bundleNote())
            }
            .setNegativeButton("Delete Note") {dialog, which ->
                executeLegend(NotePreviewLegend::class, NotePreviewLegend.ACTION_DELETE_NOTE, bundleNote())
            }
            .setNeutralButton("Close") { dialog, which ->
                // close dialog
            }

        tvNoteTitle = layout.findViewById(R.id.tv_note_title_layout_note_preview_dialog)
        tvNoteText = layout.findViewById(R.id.tv_note_text_layout_note_preview_dialog)
        tvNoteDate = layout.findViewById(R.id.tv_note_date_layout_note_preview_dialog)
        tvNoteTitle.text = mNote.title
        tvNoteText.text = mNote.text
        tvNoteDate.text = mNote.date

        return builder.create()
    }

    private fun bundleNote(): Bundle {
        val bundle = Bundle()
        bundle.putParcelable(Note.EXTRA_NOTE, mNote)
        return bundle
    }

}