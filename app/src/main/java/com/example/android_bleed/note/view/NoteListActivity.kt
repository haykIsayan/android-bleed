package com.example.android_bleed.note.view

import android.os.Bundle
import com.example.android_bleed.R
import com.example.android_bleed.note.NoteListFlow
import com.example.android_bleed.flow.view.FlowActivity

class NoteListActivity : FlowActivity() {
    override fun getFragmentContainerId(): Int = R.id.fl_container_activity_note_list


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        launchFlow(NoteListFlow::class)

    }







}
