package com.example.android_bleed.editing.view

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.android_bleed.R
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.authentication.AuthenticationFlow
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.data.models.User
import com.example.android_bleed.editing.CreateNoteFlow
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.flow.view.FlowFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateNoteFragment : FlowFragment() {

    override fun getLayoutResource(): Int = R.layout.fragment_create_note


    private lateinit var etNoteTitle: EditText
    private lateinit var etNoteText: EditText
    private lateinit var fabSaveNote: FloatingActionButton

    private lateinit var mCreateNoteFlow: AndroidFlow

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mCreateNoteFlow = getFlowByName(CreateNoteFlow::class.java.name)?:return

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
            launchAuthFlow()
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
        executeFlow(mCreateNoteFlow, CreateNoteFlow.ACTION_SAVE_NOTE, bundle)
    }

    private fun launchAuthFlow() {
        val authenticationFlow = AuthenticationFlow(application = activity!!.application)
        registerFlow(authenticationFlow)
        launchFlow(authenticationFlow)

    }
}
