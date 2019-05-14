package com.example.android_bleed.editing.domain.note

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.data.repositories.NoteRepository
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.flowsteps.UserAction

class EditNoteAction : UserAction.UserApplicationAction() {


    override fun execute(application: Application): LiveData<LegendResult> {
        val data = MutableLiveData<LegendResult>()
        val thread = Thread(Runnable {

            val note = dataBundle.getParcelable<Note>(Note.EXTRA_NOTE)

            note?.apply {
                val noteRepository = NoteRepository(application)
                noteRepository.editNote(note)
                data.postValue(LegendResult(LegendResult.Status.COMPLETED))
                return@Runnable
            }
            data.postValue(LegendResult.fail("Please provide a note"))
        })
        thread.start()
        return data
    }
}