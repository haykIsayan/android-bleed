package com.example.android_bleed.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "ReminderTable", indices = [Index(value = ["reminder_date"], unique = true)])
class Reminder(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Int? = null,
    @ColumnInfo(name = "reminder_message")
    val reminderMessage: String,
    @ColumnInfo(name = "reminder_date")
    val reminderDate: String,
    @ColumnInfo(name = "reminder_time")
    val reminderTime: String,
    @ColumnInfo(name = "author_name")
    val authorName: String,
    @ColumnInfo(name = "long_date")
    val longDate: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(reminderId)
        parcel.writeString(reminderMessage)
        parcel.writeString(reminderDate)
        parcel.writeString(reminderTime)
        parcel.writeString(authorName)
        parcel.writeLong(longDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reminder> {

        const val EXTRA_REMINDER = "Extra.Reminder"

        const val REMINDER_ID = "Reminder.Id"
        const val REMINDER_MESSAGE = "Reminder.Message"

        override fun createFromParcel(parcel: Parcel): Reminder {
            return Reminder(parcel)
        }

        override fun newArray(size: Int): Array<Reminder?> {
            return arrayOfNulls(size)
        }
    }


}