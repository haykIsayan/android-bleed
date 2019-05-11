package com.example.android_bleed.authentication

import android.app.Application
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.authentication.domain.LoginAction
import com.example.android_bleed.authentication.domain.RegisterAction
import com.example.android_bleed.authentication.view.LoginFragment
import com.example.android_bleed.authentication.view.RegisterFragment
import com.example.android_bleed.main.MainLegend
import com.example.android_bleed.utilities.SlideAnimation

class AuthenticationLegend(application: Application): AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {

        return FlowGraph()

            .startWith(
                FlowVector()
                    .transitionTo(LoginFragment::class, true, SlideAnimation())
            )

            .addFlowVector(
                ACTION_LOGIN, FlowVector()
                    .execute(LoginAction())
                    .launchFlow(MainLegend::class)
            )

            .addFlowVector(
                ACTION_REGISTER, FlowVector()
                .execute(RegisterAction())
                .launchFlow(MainLegend::class)
            )

            .addFlowVector(
                ACTION_GOTO_REGISTER, FlowVector()
                    .transitionTo(RegisterFragment::class, true, SlideAnimation())
            )

            .addFlowVector(
                ACTION_GOTO_LOGIN, FlowVector()
                    .transitionTo(LoginFragment::class, true, SlideAnimation())
            )
    }

    companion object {
        const val ACTION_LOGIN = "Action.Login"
        const val ACTION_REGISTER = "Action.Register"
        const val ACTION_GOTO_REGISTER = "Action.GoTo.Register"
        const val ACTION_GOTO_LOGIN = "Action.GoTo.Login"
    }
}