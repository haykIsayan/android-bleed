package com.example.android_bleed.splash

import android.app.Application
import com.example.android_bleed.authentication.view.AuthActivity
import com.example.android_bleed.android_legends.legends.AndroidLegend
import com.example.android_bleed.utilities.SlideRightAnimation

class SplashLegend(application: Application) : AndroidLegend(application) {

    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()
            .addFlowVector(ACTION_END_SPLASH, FlowVector().startActivity(AuthActivity::class, SlideRightAnimation()))
    }

    companion object {
        const val ACTION_END_SPLASH = "Action.End.Splash"
    }
}