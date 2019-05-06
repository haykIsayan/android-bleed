package com.example.android_bleed.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_bleed.data.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteTable WHERE author_name == :userName")
    fun getNoteList(userName: String): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

}