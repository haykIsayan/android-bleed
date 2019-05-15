package com.example.android_bleed.editing.domain.reminder

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.flowsteps.UserAction
import com.example.android_bleed.data.models.Reminder
import com.example.android_bleed.data.repositories.ReminderRepository
import com.example.android_bleed.utilities.ReminderAlarmReceiver

class SaveReminderAction : UserAction.UserApplicationAction() {

    override fun execute(application: Application): LiveData<LegendResult> {
        val data = MutableLiveData<LegendResult>()
        val thread = Thread(Runnable {
            val repository = ReminderRepository(application)
            val reminder = dataBundle.getParcelable<Reminder>(Reminder.EXTRA_REMINDER)
            reminder?.apply {
                repository.createReminder(reminder)
                setNotification(reminder, application)

                val flowResource =
                    LegendResult(LegendResult.Status.COMPLETED)
                flowResource.bundle.putParcelable(Reminder.EXTRA_REMINDER, reminder)
                data.postValue(flowResource)
                return@Runnable
            }
        })
        thread.start()
        return data
    }

    private fun setNotification(reminder: Reminder, application: Application) {
        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(application, ReminderAlarmReceiver::class.java)

        intent.putExtra(Reminder.EXTRA_REMINDER, reminder.reminderDate)

        val pendingIntent = PendingIntent.getBroadcast(application,0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.longDate, pendingIntent)
        }
    }
}