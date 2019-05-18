package com.example.android_bleed.authentication.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.android_bleed.R
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.authentication.AuthenticationLegend
import com.example.android_bleed.data.models.User
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.view.LegendsActivity
import com.example.android_bleed.authentication.domain.LoginAction
import com.example.android_bleed.authentication.domain.RegisterAction

class AuthActivity : LegendsActivity() {
    override fun getFragmentContainerId(): Int = R.id.fl_main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        executeLegend(flowKlass = AuthenticationLegend::class)
        supportActionBar?.apply {
            title = "Welcome to Android Legends Demo"
        }

        executeLegend(flowKlass = AuthenticationLegend::class)

        getLegendData().observe(this, Observer {
            when (it) {
                is LoginAction.LoginResult -> {
                    Toast.makeText(this, "You have been successfully authenticated as ${it.user.userName}", Toast.LENGTH_LONG).show()
                    AuthUtilities.sCurrentUser = it.user
                }
                is RegisterAction.RegisterResult -> {
                    Toast.makeText(this, "You have benn successfully registered as ${it.user.userName}", Toast.LENGTH_LONG).show()
                    AuthUtilities.sCurrentUser = it.user
                }
            }
        })

    }
}
