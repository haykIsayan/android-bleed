package com.example.android_bleed.editing.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.android_bleed.R
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
            executeFlow(mCreateNoteFlow, CreateNoteFlow.ACTION_SAVE_NOTE)
        }


        super.onViewCreated(view, savedInstanceState)
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateNoteFragment().apply {


            }
    }
}
