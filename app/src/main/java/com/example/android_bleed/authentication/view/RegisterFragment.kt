package com.example.android_bleed.authentication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.android_bleed.R
import com.example.android_bleed.authentication.AuthenticationLegend
import com.example.android_bleed.authentication.domain.RegisterAction
import com.example.android_bleed.data.models.User
import com.example.android_bleed.android_legends.FlowResource
import com.example.android_bleed.android_legends.view.LegendsFragment


class RegisterFragment : LegendsFragment() {
    override fun getLayoutResource(): Int = R.layout.fragment_register

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText

    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getFlowData().observe(this, Observer {

            when (it) {
                is FlowResource.FailResource -> {
                    Toast.makeText(activity, it.failMessage, Toast.LENGTH_SHORT).show()
                }
                is RegisterAction.RegisterFlowResource -> {
                    Log.d("HAYK","COMPLETED")
                    Toast.makeText(activity,"You have registered as ${it.bundle.getParcelable<User>(User.EXTRA_USER)?.userName}", Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etUsername = view.findViewById(R.id.et_username_fragment_register)
        etFirstname = view.findViewById(R.id.et_firstname_fragment_register)
        etLastname = view.findViewById(R.id.et_lastname_fragment_register)
        etPassword = view.findViewById(R.id.et_password_fragment_register)

        btnRegister = view.findViewById(R.id.btn_register_fragment_register)
        btnLogin = view.findViewById(R.id.btn_login_fragment_register)

        btnRegister.setOnClickListener {
            register()
        }

        btnLogin.setOnClickListener {
            executeFlow(flowKlass = AuthenticationLegend::class, vectorTag = AuthenticationLegend.ACTION_GOTO_LOGIN)
        }

    }

    private fun register() {
        val userName = etUsername.text.toString()
        val firstName = etFirstname.text.toString()
        val lastName = etLastname.text.toString()
        val password = etPassword.text.toString()

        if (userName.isEmpty() || firstName.isEmpty()
            || lastName.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Please provide fill out all fields", Toast.LENGTH_LONG).show()
            return
        }

        val user = User(null,
            userName = userName,
            firstName = firstName,
            lastName = lastName,
            password = password
        )

        val bundle = Bundle()
        bundle.putParcelable(User.EXTRA_USER, user)
        executeFlow(flowKlass = AuthenticationLegend::class, vectorTag = AuthenticationLegend.ACTION_REGISTER, bundle = bundle)
    }

}
