package com.example.android_bleed.editing.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.view.LegendsFragment
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.authentication.AuthenticationLegend
import com.example.android_bleed.data.models.Reminder
import com.example.android_bleed.data.models.User
import com.example.android_bleed.editing.CreateReminderLegend
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.util.*


class CreateReminderFragment : LegendsFragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    override fun getLayoutResource(): Int = R.layout.fragment_create_reminder

    private lateinit var etReminderMessage: EditText

    private lateinit var btnSelectedDate: Button
    private lateinit var btnSelectedTime: Button

    private val mCalendar = Calendar.getInstance()

    private lateinit var fabSaveReminder: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editingActivity = (activity as EditingActivity)
        editingActivity.supportActionBar?.title = "Create a reminder"
        editingActivity.supportActionBar?.setIcon(R.drawable.ic_action_reminder)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editingActivity.supportActionBar?.setBackgroundDrawable(editingActivity.getDrawable(R.drawable.rounded_bg_orange_v2))
        }

        etReminderMessage = view.findViewById(R.id.et_reminder_message_fragment_create_reminder)
        btnSelectedDate = view.findViewById(R.id.btn_date_fragment_create_reminder)
        btnSelectedTime = view.findViewById(R.id.btn_time_fragment_create_reminder)

        fabSaveReminder = view.findViewById(R.id.fab_save_reminder_create_reminder)

        fabSaveReminder.setOnClickListener {
            AuthUtilities.sCurrentUser?.apply {
                saveReminder(this)
                return@setOnClickListener
            }
            startLegend(AuthenticationLegend::class)
        }

        btnSelectedDate.setOnClickListener {
            openDatePicker()
        }

        btnSelectedTime.setOnClickListener {
            openTimePicker()
        }

        setCurrentTime()

    }

    private fun setCurrentTime() {
        btnSelectedDate.text = DateFormat.getDateInstance(DateFormat.LONG).format(mCalendar.time)
        val date = DateFormat.getInstance().format(mCalendar.time).split(" ")
        btnSelectedTime.text = "${date[1]} ${date[2]}"
    }

    private fun openDatePicker() {
        val datePickerDialog = DatePickerDialog(
            activity,
            this,
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun openTimePicker() {
        val timePickerDialog = TimePickerDialog(
            activity, this,
            mCalendar.get(Calendar.HOUR_OF_DAY),
            mCalendar.get(Calendar.MINUTE),
            true)
        timePickerDialog.show()
    }


    private fun saveReminder(user: User) {
        val reminder = Reminder(
            null,
            etReminderMessage.text.toString(),
            btnSelectedDate.text.toString(),
            btnSelectedTime.text.toString(),
            user.userName,
            mCalendar.timeInMillis
        )

        val bundle = Bundle()
        bundle.putParcelable(Reminder.EXTRA_REMINDER, reminder)

        executeLegend(CreateReminderLegend::class, CreateReminderLegend.ACTION_SAVE_REMINDER, bundle)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        mCalendar.set(Calendar.MINUTE, minute)
        mCalendar.set(Calendar.SECOND, 0)

        setCurrentTime()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.YEAR, year)

        setCurrentTime()
    }

}
