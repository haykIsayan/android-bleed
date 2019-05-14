package com.example.android_bleed.editing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.view.LegendsActivity

class EditingActivity : LegendsActivity() {
    override fun getFragmentContainerId(): Int = R.id.fl_container_activity_editing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editing)
    }
}
