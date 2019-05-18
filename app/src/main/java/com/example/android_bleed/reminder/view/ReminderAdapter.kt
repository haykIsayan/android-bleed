package com.example.android_bleed.reminder.view

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bleed.R
import com.example.android_bleed.data.models.Reminder

class ReminderAdapter(val reminderClickListener: ReminderClickListener) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private val mReminderList: ArrayList<Reminder> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(itemView)
    }

    override fun getItemCount(): Int = mReminderList.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(mReminderList[position])
        holder.itemView.setOnClickListener {
            reminderClickListener.onReminderClick(mReminderList[position])
        }
    }

    fun setReminderList(reminderList: List<Reminder>) {
        mReminderList.clear()
        mReminderList.addAll(reminderList)
        notifyDataSetChanged()
    }

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvReminderMessage: TextView = itemView.findViewById(R.id.tv_reminder_message_item_reminder)
        private val tvReminderDate: TextView = itemView.findViewById(R.id.tv_reminder_date_item_reminder)
        private val tvReminderTime: TextView = itemView.findViewById(R.id.tv_reminder_time_item_reminder)

        fun bind(reminder: Reminder) {

            if (!reminder.reminderMessage.isEmpty()) {
                tvReminderMessage.text = reminder.reminderMessage
            }
            tvReminderDate.text = reminder.reminderDate
            tvReminderTime.text = reminder.reminderTime


        }
    }

    interface ReminderClickListener {
        fun onReminderClick(reminder: Reminder)
    }
}