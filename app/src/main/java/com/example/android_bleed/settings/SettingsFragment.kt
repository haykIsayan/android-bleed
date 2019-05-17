package com.example.android_bleed.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.view.LegendsFragment
import com.example.android_bleed.authentication.AuthUtilities
import com.example.android_bleed.authentication.AuthenticationLegend


class SettingsFragment : LegendsFragment() {
    override fun getLayoutResource(): Int {
        return R.layout.fragment_profile
    }


    private lateinit var btnLogout: Button
    private lateinit var tvUsername: TextView
    private lateinit var tvFirstname: TextView
    private lateinit var tvLastname: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUsername = view.findViewById(R.id.tv_username_fragment_profile)
        tvFirstname = view.findViewById(R.id.tv_firstname_fragment_profile)
        tvLastname = view.findViewById(R.id.tv_lastname_fragment_profile)
        btnLogout = view.findViewById(R.id.btn_logout_fragment_profile)

        val user = AuthUtilities.sCurrentUser!!

        tvUsername.text = user.userName
        tvFirstname.text = user.firstName
        tvLastname.text = user.lastName

        btnLogout.setOnClickListener {
            AuthUtilities.sCurrentUser = null
            startLegend(AuthenticationLegend::class)
        }

    }
}
