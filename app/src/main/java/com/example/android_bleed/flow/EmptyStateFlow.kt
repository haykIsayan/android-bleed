package com.example.android_bleed.flow

import android.app.Application

class EmptyStateFlow(application: Application) : AndroidFlow(application){
    override fun onCreateFlowGraph(): FlowGraph {
        return FlowGraph()

    }
}