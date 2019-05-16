package com.example.android_bleed.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.view.LegendsFragment
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.authentication.AuthenticationLegend


class SettingsFragment : LegendsFragment() {
    override fun getLayoutResource(): Int {
        return R.layout.fragment_settings
    }


    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogout = view.findViewById(R.id.btn_logout_fragment_profile)

        btnLogout.setOnClickListener {

            AuthUtilities.sCurrentUser = null
            startLegend(AuthenticationLegend::class)
        }


    }
}
