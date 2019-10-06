package com.example.modular.app.features.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.modular.R
import com.example.modular.app.core.InflatableFragment
import com.example.modular.app.core.InflatableFragmentDelegate

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager?.takeIf { savedInstanceState == null }
            ?.beginTransaction()
            ?.replace(R.id.container, LoginFragment())
            ?.commit()
    }
}

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class LoginFragment : Fragment(), InflatableFragment by InflatableFragmentDelegate(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model(view(intents()))
    }
}