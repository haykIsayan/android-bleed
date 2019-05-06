package com.example.android_bleed.main.domain

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.repositories.NoteRepository
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.flowsteps.UserAction

class GetNoteListAction : UserAction.UserApplicationAction() {


    override fun execute(application: Application): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()

        val thread = Thread(Runnable {

            val noteRepository = NoteRepository(application)

            val noteList = noteRepository.getNotesByAuthorUsername("HaykIsayan98")
            if (noteList != null && !noteList.isEmpty()) {
                val flowResource = FlowResource(status = FlowResource.Status.COMPLETED)
                val noteArrayList = arrayListOf<Note>()
                noteArrayList.addAll(noteList)
                flowResource.bundle.putParcelableArrayList(Note.EXTRA_NOTE_LIST, noteArrayList)
                data.postValue(flowResource)
                return@Runnable
            }
            data.postValue(FlowResource.FailResource())
        })
        thread.start()
        return data
    }



}