package com.example.android_bleed.note.domain

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.android_legends.flowsteps.UserAction
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.data.repositories.NoteRepository

class DeleteNoteAction : UserAction.UserApplicationAction() {
    override fun execute(application: Application): LiveData<LegendResult> {

        val data = MutableLiveData<LegendResult>()
        val thread = Thread(Runnable {
            val repository = NoteRepository(application)
            val note = dataBundle.getParcelable<Note>(Note.EXTRA_NOTE)
            note?.apply {
                repository.deleteNote(note)

                val flowResource =
                    LegendResult(LegendResult.Status.COMPLETED)

                data.postValue(flowResource)
                return@Runnable
            }
        })
        thread.start()
        return data
    }
}