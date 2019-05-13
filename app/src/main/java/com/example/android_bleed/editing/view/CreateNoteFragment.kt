package com.example.android_bleed.editing.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.android_bleed.R
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.authentication.AuthenticationLegend
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.data.models.User
import com.example.android_bleed.editing.CreateNoteLegend
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.android_legends.view.LegendsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateNoteFragment : LegendsFragment() {

    override fun getLayoutResource(): Int = R.layout.fragment_create_note


    private lateinit var etNoteTitle: EditText
    private lateinit var etNoteText: EditText
    private lateinit var fabSaveNote: FloatingActionButton

    private lateinit var mCreateNoteLegend: AndroidLegend

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        this.etNoteTitle = view.findViewById(R.id.et_note_title_fragment_create_note)
        this.etNoteText = view.findViewById(R.id.et_note_text_fragment_create_note)
        this.fabSaveNote = view.findViewById(R.id.fab_save_note_fragment_create_note)

        this.fabSaveNote.setOnClickListener {
            AuthUtilities.sCurrentUser?.apply {
                saveNote(this)
                return@setOnClickListener
            }
            executeLegend(AuthenticationLegend::class)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun saveNote(user: User) {
        val noteTitle = etNoteTitle.text.toString()
        val noteText = etNoteText.text.toString()
        if (noteTitle.isEmpty() || noteText.isEmpty()) {
            Toast.makeText(activity, "Please provide a title and a text for the note", Toast.LENGTH_LONG).show()
            return
        }
        val note = Note(null,
            authorUsername = user.userName,
            title = etNoteTitle.text.toString(),
            text = etNoteText.text.toString()
        )
        val bundle = Bundle()
        bundle.putParcelable(Note.EXTRA_NOTE, note)
        executeLegend(CreateNoteLegend::class, CreateNoteLegend.ACTION_SAVE_NOTE, bundle)
    }
}
