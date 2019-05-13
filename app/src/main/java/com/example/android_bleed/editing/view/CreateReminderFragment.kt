package com.example.android_bleed.editing.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.text.SimpleDateFormat


class CreateReminderFragment : LegendsFragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    override fun getLayoutResource(): Int = R.layout.fragment_create_reminder

    private lateinit var etReminderMessage: EditText

    private lateinit var btnSelectedDate: Button
    private lateinit var btnSelectedTime: Button

    private val mCurrentReminder = Reminder(null, "","", "","")

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
        val c = Calendar.getInstance()
        btnSelectedDate.text = "${c.get(Calendar.YEAR)} ${c.get(Calendar.MONTH)} ${c.get(Calendar.DAY_OF_MONTH)}"
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            activity,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun openTimePicker() {
        val date = Date()
        val timePickerDialog = TimePickerDialog(activity, this, date.hours, date.minutes, true)
        timePickerDialog.show()
    }


    private fun saveReminder(user: User) {
//
//        val calendar = Calendar.getInstance()
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//        val month = calendar.get(Calendar.MONTH)
//        val year = calendar.get(Calendar.YEAR)
//
//        val reminderDate = "$year/$month/$day"
//        val reminderTime = "${calendar.get(Calendar.HOUR_OF_DAY)} : ${calendar.get(Calendar.MINUTE)}"
//        val reminder = Reminder(null, etReminderMessage.text.toString(), reminderDate, reminderTime, user.userName)



        mCurrentReminder.authorName = user.userName
        mCurrentReminder.reminderDate = DateFormat.getInstance().format(Calendar.getInstance().time)
        mCurrentReminder.reminderMessage = etReminderMessage.text.toString()
        val bundle = Bundle()
        bundle.putParcelable(Reminder.EXTRA_REMINDER, mCurrentReminder)

        executeLegend(CreateReminderLegend::class, CreateReminderLegend.ACTION_SAVE_REMINDER, bundle)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        mCurrentReminder.reminderTime = "$hourOfDay:$minute:0"
        btnSelectedTime.text = mCurrentReminder.reminderTime
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        mCurrentReminder.reminderDate = DateFormat.getInstance().format(calendar)
        btnSelectedDate.text = mCurrentReminder.reminderDate
    }

}
