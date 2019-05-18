package com.example.android_bleed.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteTable")
class Note (
    @PrimaryKey(autoGenerate = true)
    var noteId: Int?,
    @ColumnInfo(name = "author_name")
    val authorUsername: String,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "text")
    val text: String = "",
    @ColumnInfo(name = "date")
    val date: String

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(noteId!!)
        parcel.writeString(authorUsername)
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        const val EXTRA_NOTE = "Extra.Note"
        const val EXTRA_NOTE_LIST = "Extra.Note.List"
        const val EXTRA_NOTE_TITLE = "Extra.Note.Title"
        const val EXTRA_NOTE_TEXT = "Extra.Note.Text"

        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}