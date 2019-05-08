package com.example.android_bleed.editing.domain

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.data.models.User
import com.example.android_bleed.data.repositories.NoteRepository
import com.example.android_bleed.data.repositories.UserRepository
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.flowsteps.UserAction

class SaveNoteAction : UserAction.UserApplicationAction() {

    override fun execute(application: Application): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()
        val thread = Thread(Runnable {
            val repository = NoteRepository(application)
            val note = dataBundle.getParcelable<Note>(Note.EXTRA_NOTE)
            note?.apply {
                repository.createNote(note)
                val flowResource = FlowResource(FlowResource.Status.COMPLETED)
                flowResource.bundle.putParcelable(Note.EXTRA_NOTE, note)
                data.postValue(flowResource)
                return@Runnable
            }
        })
        thread.start()
        return data
    }

}