package com.example.android_bleed.splash

import android.app.Application
import com.example.android_bleed.authentication.view.AuthActivity
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.utilities.SlideAnimation

class SplashLegend(application: Application) : AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .addFlowVector(ACTION_END_SPLASH, FlowVector().startActivity(AuthActivity::class, SlideAnimation()))
    }

    companion object {
        const val ACTION_END_SPLASH = "Action.End.Splash"
    }
}