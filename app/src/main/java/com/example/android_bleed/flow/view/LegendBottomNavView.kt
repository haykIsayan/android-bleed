package com.example.android_bleed.flow.view

import android.content.Context
import com.example.android_bleed.flow.AndroidFlow
import com.google.android.material.bottomnavigation.BottomNavigationView

class LegendBottomNavView(context: Context?) : BottomNavigationView(context) {

    private lateinit var mFlow: AndroidFlow

    fun setFlow(flow: AndroidFlow) {
        this.mFlow = flow
    }

    init {

//
//        setOnNavigationItemSelectedListener {
//            val tag = it.itemId.toString()
//            mFlow.execute(tag)
//
//            true
//        }


    }



}