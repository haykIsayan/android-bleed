package com.example.android_bleed.main

import android.os.Bundle
import android.view.MenuItem
import com.example.android_bleed.R
import com.example.android_bleed.data.models.User
import com.example.android_bleed.android_legends.AndroidLegend
import com.example.android_bleed.android_legends.view.LegendsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : LegendsActivity() {

    override fun getFragmentContainerId(): Int {
        return R.id.fl_main_container_activity_main
    }

    private var mCurrentUser: User? = null

    private lateinit var bnvMainNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.bnvMainNavigation = findViewById(R.id.bnv_nav_view_activity_main)
        this.mCurrentUser = intent.getParcelableExtra(User.EXTRA_USER)

        this.bnvMainNavigation.setOnNavigationItemSelectedListener {
            onNavigationItemSelected(menuItem = it)
        }
    }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val bundle = Bundle()
        bundle.putString(User.EXTRA_USERNAME, mCurrentUser?.userName)

        executeFlow(
            flowKlass = MainLegend::class,
            vectorTag = menuItem.title.toString(),
            bundle = bundle
        )
        return true
    }

}
