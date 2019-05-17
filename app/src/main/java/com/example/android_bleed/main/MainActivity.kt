package com.example.android_bleed.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import com.example.android_bleed.R
import com.example.android_bleed.data.models.User
import com.example.android_bleed.android_legends.view.LegendsActivity
import com.example.android_bleed.authentication.AuthUtilities
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

        setSupportActionBar(findViewById(R.id.tb_action_bar_activity_main))

        supportActionBar?.apply {
            this.title = AuthUtilities.sCurrentUser?.userName
            this.setLogo(R.mipmap.baseline_person_white_18dp)
        }

        this.bnvMainNavigation = findViewById(R.id.bnv_nav_view_activity_main)
        this.mCurrentUser = intent.getParcelableExtra(User.EXTRA_USER)


        this.bnvMainNavigation.setOnNavigationItemSelectedListener {
            onNavigationItemSelected(menuItem = it)
        }
    }

    fun setSubTitle(subtitle: String) {
        supportActionBar?.subtitle = subtitle
    }

    fun selectBottomNavigation(@IdRes menuItemId: Int) {
        if (bnvMainNavigation.selectedItemId == menuItemId) return
        when (menuItemId) {
            R.id.menu_note_list -> bnvMainNavigation.menu.getItem(0).isChecked = true
            R.id.menu_reminder_list ->  bnvMainNavigation.menu.getItem(1).isChecked = true
        }
    }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val bundle = Bundle()
        bundle.putString(User.EXTRA_USERNAME, mCurrentUser?.userName)

        executeLegend(
            flowKlass = MainLegend::class,
            vectorTag = menuItem.title.toString(),
            bundle = bundle
        )
        return true
    }

}
