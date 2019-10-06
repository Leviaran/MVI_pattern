package com.example.modular.app.features.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch


fun LoginFragment.view(intents: Channel<Intents>): Pair<Channel<Intents>, SendChannel<UiModel>> {
    return intents to lifecycleScope.actor {
        for(model in channel) {
            updateProgress(model)
            updateLoginButton(model, intents)
            updateRegisterButton(model, intents)
            updateErrorTextView(model)
            updateNavigation(model)
        }
    }
}

private fun LoginFragment.updateNavigation(uiModel: UiModel) {
    uiModel.authenticationResponse
        ?.takeIf { it.success }
        ?.also {

            activity?.finish()
        }
}

private fun LoginFragment.updateErrorTextView(uiModel: UiModel) {
    errorTextView.text = uiModel.error ?: ""
}

private fun LoginFragment.updateProgress(uiModel: UiModel) {
    progressBar.visibility = if(uiModel.progress) View.VISIBLE else View.GONE
}

private fun LoginFragment.updateLoginButton(uiModel: UiModel, intents: SendChannel<Intents>) {
    if(uiModel.progress) loginButton.setOnClickListener(null)
    else loginButton.setOnClickListener {
        lifecycleScope.launch(SupervisorJob()  + Dispatchers.IO) {
            intents.send(
                RequestLogin(
                    userNameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            )
        }
    }
}

private fun LoginFragment.updateRegisterButton(uiModel: UiModel, intents: SendChannel<Intents>) {
    if (uiModel.progress) registerButton.setOnClickListener(null) else {
        lifecycleScope.launch(SupervisorJob() + Dispatchers.IO) {
            intents.send(
                RequestRegister(
                    userNameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            )
        }
    }
}