package com.example.android_bleed.flow.flowsteps.fragment

import androidx.annotation.AnimRes

class CustomAnimation (
    @AnimRes
    val enterAnimation: Int,
    @AnimRes
    val exitAnimation: Int,
    @AnimRes
    val popEnterAnimation: Int,
    @AnimRes
    val popExitAnimation: Int) : FragmentAnimation()