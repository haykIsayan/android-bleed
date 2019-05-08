package com.example.android_bleed.authentication.view

import android.os.Bundle
import com.example.android_bleed.R
import com.example.android_bleed.authentication.AuthenticationFlow
import com.example.android_bleed.flow.view.FlowActivity

class AuthActivity : FlowActivity() {
    override fun getFragmentContainerId(): Int = R.id.fl_main_fragment_container

    private lateinit var mAuthenticationFlow: AuthenticationFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        this.mAuthenticationFlow = AuthenticationFlow(application)
        registerFlow(mAuthenticationFlow)
        launchFlow(mAuthenticationFlow)
    }


    fun getAuthFlow() = mAuthenticationFlow





}
