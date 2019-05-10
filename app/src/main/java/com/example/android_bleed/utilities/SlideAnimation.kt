package com.example.android_bleed.utilities

import com.example.android_bleed.R
import com.example.android_bleed.flow.flowsteps.fragment.CustomAnimation

class SlideAnimation : CustomAnimation(
    R.anim.custom_slide_enter,
    R.anim.custom_slide_exit,
    R.anim.custom_pop_enter,
    R.anim.custom_pop_exit
)