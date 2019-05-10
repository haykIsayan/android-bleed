package com.example.android_bleed.authentication.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.android_bleed.R
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.authentication.AuthenticationLegend
import com.example.android_bleed.data.models.User
import com.example.android_bleed.android_legends.FlowResource
import com.example.android_bleed.android_legends.view.LegendsActivity

class AuthActivity : LegendsActivity() {
    override fun getFragmentContainerId(): Int = R.id.fl_main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        executeFlow(flowKlass = AuthenticationLegend::class)

        getFlowData().observe(this, Observer {
            if (it.status == FlowResource.Status.COMPLETED) {
                val user = it.bundle.getParcelable<User>(User.EXTRA_USER)
                user?.apply {
                    AuthUtilities.sCurrentUser = this
                }
            }
        })

    }
}
