package com.example.android_bleed.note.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bleed.R
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.view.LegendsFragment
import com.example.android_bleed.main.MainActivity
import com.example.android_bleed.note.NotePreviewLegend
import kotlin.collections.ArrayList


class NoteListFragment : LegendsFragment(), NoteListAdapter.OnNoteClickListener {

    override fun getLayoutResource(): Int = R.layout.fragment_note_list

    private lateinit var llEmptyState: LinearLayout
    private lateinit var rvNoteList: RecyclerView

    private lateinit var mNoteListAdapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        getLegendData().observe(this, Observer {
            when (it.status) {
                LegendResult.Status.FAILED -> {
                    llEmptyState.visibility = View.VISIBLE
                }
                LegendResult.Status.COMPLETED -> {
                    val noteList: ArrayList<Note> = it.bundle.getParcelableArrayList(Note.EXTRA_NOTE_LIST) ?: return@Observer
                    llEmptyState.visibility = View.GONE
                    Toast.makeText(activity, noteList.size.toString(), Toast.LENGTH_SHORT).show()
                    mNoteListAdapter.setNoteList(noteList)
                }
                else -> {

                }
            }
        })

        val mainActivity = (activity as MainActivity)
        mainActivity.selectBottomNavigation(R.id.menu_note_list)
        mainActivity.setSubTitle("My Notes")

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        rvNoteList = view.findViewById(R.id.rv_note_list_fragment_note_list)
        llEmptyState = view.findViewById(R.id.ll_empty_view_fragment_note_list)
        mNoteListAdapter = NoteListAdapter(ArrayList(), this)
        rvNoteList.adapter = mNoteListAdapter
        rvNoteList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onNoteClick(v: View?, note: Note) {
        val bundle = Bundle()
        bundle.putParcelable(Note.EXTRA_NOTE, note)

        startLegend(NotePreviewLegend::class, bundle = bundle)
    }

}
