package com.example.android_bleed.data.repositories

import android.app.Application
import android.content.Context
import com.example.android_bleed.data.databases.ReminderDatabase
import com.example.android_bleed.data.models.Reminder

class ReminderRepository(val context: Context) {

    fun getRemindersByAuthorName(userName: String): List<Reminder>? {
        val reminderDatabase = ReminderDatabase.getDatabase(context)
        return reminderDatabase?.reminderDao()?.getReminderList(userName)
    }

    fun createReminder(reminder: Reminder) {
        val reminderDatabase = ReminderDatabase.getDatabase(context)
        reminderDatabase?.reminderDao()?.insertReminder(reminder)
    }

    fun getReminderByDate(reminderDate: String): Reminder? {
        val reminderDatabase = ReminderDatabase.getDatabase(context)
        return reminderDatabase?.reminderDao()?.getReminderByDate(reminderDate)

    }

}