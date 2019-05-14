package com.example.android_bleed.note.domain

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.data.repositories.NoteRepository
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.flowsteps.UserAction

class GetNoteListAction : UserAction.UserApplicationAction() {


    override fun execute(application: Application): LiveData<LegendResult> {
        val data = MutableLiveData<LegendResult>()

        val thread = Thread(Runnable {

            val noteRepository = NoteRepository(application)

            val noteList = noteRepository.getNotesByAuthorUsername(AuthUtilities.sCurrentUser!!.userName)
            if (noteList != null && !noteList.isEmpty()) {
                val flowResource =
                    LegendResult(status = LegendResult.Status.COMPLETED)
                val noteArrayList = arrayListOf<Note>()
                noteArrayList.addAll(noteList)
                flowResource.bundle.putParcelableArrayList(Note.EXTRA_NOTE_LIST, noteArrayList)
                data.postValue(flowResource)
                return@Runnable
            }
            data.postValue(LegendResult.FailResource())
        })
        thread.start()
        return data
    }



}