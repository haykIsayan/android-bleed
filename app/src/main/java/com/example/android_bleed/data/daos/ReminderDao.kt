package com.example.android_bleed.data.daos

import androidx.room.*
import com.example.android_bleed.data.models.Reminder

@Dao
interface ReminderDao {

    @Query("SELECT * FROM ReminderTable WHERE author_name == :userName")
    fun getReminderList(userName: String): List<Reminder>

    @Query("SELECT * FROM ReminderTable WHERE reminder_date = :reminderDate")
    fun getReminderByDate(reminderDate: String): Reminder

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminder: Reminder)

    @Update
    fun updateNote(vararg reminder: Reminder)

}