package com.example.android_bleed.authentication

import android.app.Application
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.authentication.domain.LoginAction
import com.example.android_bleed.authentication.domain.RegisterAction
import com.example.android_bleed.authentication.view.AuthActivity
import com.example.android_bleed.authentication.view.LoginFragment
import com.example.android_bleed.authentication.view.RegisterFragment
import com.example.android_bleed.main.MainLegend
import com.example.android_bleed.utilities.SlideLeftAnimation
import com.example.android_bleed.utilities.SlideRightAnimation

class AuthenticationLegend(application: Application): AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .setRoot(AuthActivity::class, SlideLeftAnimation())

            .startWith(
                FlowVector()
                    .transitionTo(LoginFragment::class, false)
            )

            .addFlowVector(
                ACTION_LOGIN, FlowVector()
                    .execute(LoginAction())
                    .startLegend(MainLegend::class)
            )

            .addFlowVector(
                ACTION_REGISTER, FlowVector()
                .execute(RegisterAction())
                .startLegend(MainLegend::class)
            )

            .addFlowVector(
                ACTION_GOTO_REGISTER, FlowVector()
                    .transitionTo(RegisterFragment::class, false,  fragmentAnimation = SlideRightAnimation())
            )

            .addFlowVector(
                ACTION_GOTO_LOGIN, FlowVector()
                    .transitionTo(LoginFragment::class, false, fragmentAnimation = SlideLeftAnimation())
            )
    }

    companion object {
        const val ACTION_LOGIN = "Action.Login"
        const val ACTION_REGISTER = "Action.Register"
        const val ACTION_GOTO_REGISTER = "Action.GoTo.Register"
        const val ACTION_GOTO_LOGIN = "Action.GoTo.Login"
    }
}