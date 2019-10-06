package com.example.modular.app.features.login

import com.example.modular.entities.AuthenticationResponse
import kotlinx.coroutines.channels.Channel

fun LoginFragment.intents() = Channel<Intents>()

sealed class Intents
data class RequestLogin(val userName: String, var password: String) : Intents()
data class RequestRegister(val userName: String, var password: String) : Intents()

data class UiModel(
    val error: String? = null,
    val progress: Boolean = false,
    val authenticationResponse: AuthenticationResponse? = null
)