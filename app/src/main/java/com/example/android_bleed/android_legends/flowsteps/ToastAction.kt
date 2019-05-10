package com.example.android_bleed.android_legends.flowsteps

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_bleed.android_legends.FlowResource

class ToastAction (val message: String = "",
                   @StringRes val messageRes: Int = -1,
                   val toastDuration: Int = Toast.LENGTH_SHORT): UserAction(){

    override fun execute(): LiveData<FlowResource> {
        val data = MutableLiveData<FlowResource>()

        data.postValue(
            ToastResource(
                message = message,
                messageRes = messageRes,
                toastDuration = toastDuration
            )
        )
        return data
    }


    // todo
//    @IntDef({Toast.LENGTH_SHORT Toast.LENGTH_LONG})
//    @Retention(RetentionPolicy.SOURCE)
//    interface TosdDuration {
//
//    }
//
//
//    override fun invoke(): LiveData<FlowResource> {
//        val data = MutableLiveData<FlowResource>()
//        data.postValue(ToastResource())
//        return data
//    }




    data class ToastResource (val message: String = "", @StringRes val messageRes: Int = -1,
                              val toastDuration: Int = Toast.LENGTH_SHORT) : FlowResource(Status.COMPLETED)

}