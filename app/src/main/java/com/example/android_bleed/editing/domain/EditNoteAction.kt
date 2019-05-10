package com.example.android_bleed.editing.domain

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.data.repositories.NoteRepository
import com.example.android_bleed.android_legends.FlowResource
import com.example.android_bleed.android_legends.flowsteps.UserAction

class EditNoteAction : UserAction.UserApplicationAction() {


    override fun execute(application: Application): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()
        val thread = Thread(Runnable {

            val note = dataBundle.getParcelable<Note>(Note.EXTRA_NOTE)

            note?.apply {
                val noteRepository = NoteRepository(application)
                noteRepository.editNote(note)
                data.postValue(FlowResource(FlowResource.Status.COMPLETED))
                return@Runnable
            }
            data.postValue(FlowResource.fail("Please provide a note"))
        })
        thread.start()
        return data
    }
}