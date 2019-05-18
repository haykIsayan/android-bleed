package com.example.android_bleed.reminder.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.view.LegendsFragment
import com.example.android_bleed.data.models.Reminder


class ReminderPreviewFragment : LegendsFragment() {
    override fun getLayoutResource(): Int = R.layout.fragment_reminder_preview

    private lateinit var mCurrentReminder: Reminder

    private lateinit var tvReminderMessage: TextView
    private lateinit var tvReminderTime: TextView
    private lateinit var tvReminderDate: TextView

    private lateinit var btnDeleteReminder: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCurrentReminder = arguments?.getParcelable(Reminder.EXTRA_REMINDER) ?: return

        tvReminderMessage = view.findViewById(R.id.tv_reminder_message_fragment_reminder_preview)
        tvReminderTime = view.findViewById(R.id.tv_reminder_time_fragment_reminder_preview)
        tvReminderDate = view.findViewById(R.id.tv_reminder_date_fragment_reminder_preview)

        btnDeleteReminder = view.findViewById(R.id.btn_delete_reminder_fragment_reminder_preview)

        tvReminderMessage.text = mCurrentReminder.reminderMessage
        tvReminderTime.text = mCurrentReminder.reminderTime
        tvReminderDate.text = mCurrentReminder.reminderDate



    }


}
