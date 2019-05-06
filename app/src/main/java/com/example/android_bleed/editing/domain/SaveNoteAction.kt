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
            val note = Note(authorUsername = "HaykIsayan98", title = "DUMMIE", text = "ASDALKSJDLKASNDKL")
            repository.createNote(note)

            val registerResource = FlowResource(FlowResource.Status.COMPLETED)
            registerResource.bundle.putParcelable(User.EXTRA_USER, note)

            data.postValue(registerResource)

        })
        thread.start()
        return data
    }



}