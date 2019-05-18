package com.example.android_bleed.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_bleed.data.daos.ReminderDao
import com.example.android_bleed.data.models.Reminder

@Database(entities = [Reminder::class], version = 2)
abstract class ReminderDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {

        private var INSTANCE: ReminderDatabase? = null

        fun getDatabase(context: Context): ReminderDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.inMemoryDatabaseBuilder(context, ReminderDatabase::class.java).build()
            }
            return INSTANCE
        }
    }


}