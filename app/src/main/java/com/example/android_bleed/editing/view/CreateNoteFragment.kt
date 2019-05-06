package com.example.android_bleed.editing.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bleed.R
import com.example.android_bleed.editing.CreateNoteFlow
import com.example.android_bleed.flow.view.FlowFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateNoteFragment : FlowFragment() {

    override fun getLayoutResource(): Int = R.layout.fragment_create_note


    private lateinit var fabSaveNote: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        this.fabSaveNote = view.findViewById(R.id.fab_save_note_fragment_create_note)

        this.fabSaveNote.setOnClickListener {

            executeFlow(CreateNoteFlow.ACTION_SAVE_NOTE)

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
