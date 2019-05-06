package com.example.android_bleed.data.repositories

import android.content.Context
import com.example.android_bleed.data.databases.NoteDatabase
import com.example.android_bleed.data.models.Note

class NoteRepository(val context: Context) {

    fun getNotesByAuthorUsername(userName: String): List<Note>? =
        NoteDatabase.getDatabase(context = context)?.noteDao()?.getNoteList(userName)

    fun createNote(note: Note) = NoteDatabase.getDatabase(context = context)?.noteDao()?.insertNote(note = note)

}