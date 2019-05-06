package com.example.android_bleed.authentication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.android_bleed.R
import com.example.android_bleed.authentication.AuthenticationFlow
import com.example.android_bleed.data.models.User
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.view.FlowFragment


class LoginFragment : FlowFragment() {
    override fun getLayoutResource(): Int = R.layout.fragment_login

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getFlowData().observe(this, Observer {
            when (it) {
                is FlowResource.FailResource -> Toast.makeText(activity, it.failMessage, Toast.LENGTH_LONG).show()
            }
        })
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etUsername = view.findViewById(R.id.et_username_fragment_login)
        etPassword = view.findViewById(R.id.et_password_fragment_login)

        btnRegister = view.findViewById(R.id.btn_register_fragment_login)

        btnLogin = view.findViewById(R.id.btn_login_fragment_login)

        btnLogin.setOnClickListener {

            val bundle = Bundle()
            bundle.putString(User.EXTRA_USERNAME, etUsername.text.toString())
            bundle.putString(User.EXTRA_PASSWORD, etPassword.text.toString())

            executeFlow(
                vectorTag = AuthenticationFlow.ACTION_LOGIN,
                bundle = bundle
            )
        }

        btnRegister.setOnClickListener {
            executeFlow(AuthenticationFlow.ACTION_GOTO_REGISTER)
        }

    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String){}
    }
}
