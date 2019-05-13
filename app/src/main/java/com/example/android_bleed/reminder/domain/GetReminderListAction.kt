package com.example.android_bleed.reminder.domain

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.android_legends.FlowResource
import com.example.android_bleed.android_legends.flowsteps.UserAction
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.data.models.Reminder
import com.example.android_bleed.data.repositories.ReminderRepository

class GetReminderListAction : UserAction.UserApplicationAction() {


    override fun execute(application: Application): LiveData<FlowResource> {

        val data = MutableLiveData<FlowResource>()

        val thread = Thread(Runnable {

            val reminderRepository = ReminderRepository(application)

            val reminderList = reminderRepository.getRemindersByAuthorName(AuthUtilities.sCurrentUser!!.userName)
            if (reminderList != null && !reminderList.isEmpty()) {
                val flowResource = GetRemindersResult(reminderList)
                // todo
                val reminderArrayList = arrayListOf<Reminder>()
                reminderArrayList.addAll(reminderList)
                flowResource.bundle.putParcelableArrayList(Note.EXTRA_NOTE_LIST, reminderArrayList)
                data.postValue(flowResource)
                return@Runnable
            }
            data.postValue(FlowResource.fail("SHIT"))
        })
        thread.start()
        return data


    }

    class GetRemindersResult(val reminderList: List<Reminder>) : FlowResource(status = Status.COMPLETED)
}