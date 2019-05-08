package com.example.android_bleed.note.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bleed.R
import com.example.android_bleed.data.models.Note

class NoteListAdapter(private val noteList: ArrayList<Note>) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {


    fun setNoteList(noteList: ArrayList<Note>) {
        this.noteList.clear()
        this.noteList.addAll(noteList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNoteTitle: TextView = itemView.findViewById(R.id.et_note_title_item_note)
        private val tvNoteText: TextView = itemView.findViewById(R.id.et_note_text_item_note)

        fun bind(note: Note) {
            tvNoteTitle.text = note.title
            tvNoteText.text = note.text
        }
    }
}