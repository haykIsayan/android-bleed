package com.example.android_bleed.authentication

import android.app.Application
import com.example.android_bleed.R
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.authentication.domain.LoginAction
import com.example.android_bleed.authentication.domain.RegisterAction
import com.example.android_bleed.authentication.view.LoginFragment
import com.example.android_bleed.authentication.view.RegisterFragment
import com.example.android_bleed.flow.flowsteps.fragment.CustomAnimation
import com.example.android_bleed.main.MainActivity

class AuthenticationFlow(application: Application): AndroidFlow(application) {

    override fun onCreateFlowGraph(): FlowGraph {

        return FlowGraph()

            .setRootStep(
                FlowVector()
                    .transitionTo(
                        LoginFragment::class, true,
                        CustomAnimation(
                            R.anim.custom_open_enter,
                            R.anim.custom_open_exit,
                            R.anim.custom_pop_enter,
                            R.anim.custom_pop_exit
                        )
                    )
            )

            .addFlowVector(
                ACTION_LOGIN, FlowVector()
                .execute(LoginAction())
                .startActivity(MainActivity::class))

            .addFlowVector(
                ACTION_REGISTER, FlowVector()
                .execute(RegisterAction())
                .startActivity(MainActivity::class)
            )

            .addFlowVector(
                ACTION_GOTO_REGISTER, FlowVector()
                    .transitionTo(RegisterFragment::class)
            )

            .addFlowVector(
                ACTION_GOTO_LOGIN, FlowVector()
                    .transitionTo(LoginFragment::class)
            )
    }

    companion object {
        const val ACTION_LOGIN = "Action.Login"
        const val ACTION_REGISTER = "Action.Register"
        const val ACTION_GOTO_REGISTER = "Action.GoTo.Register"
        const val ACTION_GOTO_LOGIN = "Action.GoTo.Login"
    }
}