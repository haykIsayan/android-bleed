package com.example.android_bleed.authentication

import android.app.Application
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.authentication.domain.LoginAction
import com.example.android_bleed.authentication.domain.RegisterAction
import com.example.android_bleed.authentication.view.LoginFragment
import com.example.android_bleed.authentication.view.RegisterFragment
import com.example.android_bleed.main.MainActivity
import com.example.android_bleed.utilities.SlideAnimation

class AuthenticationFlow(application: Application): AndroidFlow(application) {

    override fun onCreateFlowGraph(): FlowGraph {

        return FlowGraph()

            .setRootStep(
                FlowVector()
                    .transitionTo(LoginFragment::class, true, SlideAnimation())
            )

            .addFlowVector(
                ACTION_LOGIN, FlowVector()
                .execute(LoginAction())
                .startActivity(MainActivity::class, SlideAnimation()))

            .addFlowVector(
                ACTION_REGISTER, FlowVector()
                .execute(RegisterAction())
                .startActivity(MainActivity::class, SlideAnimation())
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