package com.example.modular.app.features.login

import androidx.lifecycle.lifecycleScope
import com.example.modular.domain.usecase.requestLogin
import com.example.modular.domain.usecase.requestRegister
import com.example.modular.entities.AuthenticationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

fun LoginFragment.model(channels: Pair<Channel<Intents>, SendChannel<UiModel>>) {
    lifecycleScope.handleIntentsAndUpdateUiModels(channels.first, channels.second)
}

private fun CoroutineScope.handleIntentsAndUpdateUiModels(
    intents: Channel<Intents>,
    uiModels: SendChannel<UiModel>,
    useCase: LoginUseCases = LoginUseCases(intents, uiModels)
) = launch(SupervisorJob() + Dispatchers.IO) {
    uiModels.send(UiModel())
    for(intent in intents) {
        when (intent) {
            is RequestLogin -> useCase.login(intent)
            is RequestRegister -> useCase.register(intent)
        }
    }
}

private data class LoginUseCases(
    val intents: Channel<Intents>,
    val uiModels: SendChannel<UiModel>,
    val loginUseCase: suspend (String?, String?) -> AuthenticationResponse = loginRequest,
    val registerUseCase: suspend (String?, String?) -> AuthenticationResponse = registerRequest,
    val login: suspend (RequestLogin) -> Unit = loginRequester(uiModels, loginUseCase),
    val register: suspend (RequestRegister) -> Unit = registerRequester(uiModels, registerUseCase)
)

private fun registerRequester(
    view: SendChannel<UiModel>,
    registerUseCase: suspend (String?, String?) -> AuthenticationResponse
) : suspend (RequestRegister) -> Unit = {
    view.send(UiModel(progress = true))
    val response = registerUseCase(it.userName, it.password)
    view.send(UiModel(response.errorMessage,false, response))
}

private fun loginRequester(
    view: SendChannel<UiModel>,
    loginUseCase: suspend (String?, String?) -> AuthenticationResponse
) : suspend (RequestLogin) -> Unit = {
    view.send(UiModel(progress = true))
    val response = loginUseCase(it.userName, it.password)
    view.send(UiModel(response.errorMessage, false, response))
}



private val loginRequest: suspend (String?, String?) -> AuthenticationResponse = {
    userName, password -> requestLogin(checkNotNull(userName), checkNotNull(password)).blockingGet()
}

private val registerRequest: suspend (String?, String?) -> AuthenticationResponse = {
    userName, password -> requestRegister(checkNotNull(userName), checkNotNull(password)).blockingGet()
}