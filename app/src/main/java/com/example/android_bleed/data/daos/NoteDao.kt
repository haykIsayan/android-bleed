package com.example.android_bleed.data.daos

import androidx.room.*
import com.example.android_bleed.data.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteTable WHERE author_name == :userName")
    fun getNoteList(userName: String): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update
    fun updateNote(vararg note: Note)

}