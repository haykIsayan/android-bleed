package com.example.android_bleed.editing.domain.note

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.data.repositories.NoteRepository
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.flowsteps.UserAction

class SaveNoteAction : UserAction.UserApplicationAction() {

    override fun execute(application: Application): LiveData<LegendResult> {
        val data = MutableLiveData<LegendResult>()
        val thread = Thread(Runnable {
            val repository = NoteRepository(application)
            val note = dataBundle.getParcelable<Note>(Note.EXTRA_NOTE)
            note?.apply {
                repository.createNote(note)
                val flowResource =
                    LegendResult(LegendResult.Status.COMPLETED)
                flowResource.bundle.putParcelable(Note.EXTRA_NOTE, note)
                data.postValue(flowResource)
                return@Runnable
            }
        })
        thread.start()
        return data
    }

}