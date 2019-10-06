package com.example.modular.domain.usecase

import com.example.modular.domain.repositories.authentication.AuthenticationRepository
import com.example.modular.domain.repositories.authentication.authenticationRepository
import com.example.modular.entities.AuthenticationResponse
import com.example.modular.entities.UserCredentials
import io.reactivex.Single

private const val MINIMIMUM_USER_CREDENTIAL_LETTER = 5

private fun String?.isValidUserCredential(): Boolean {
    return this != null && this.length >= MINIMIMUM_USER_CREDENTIAL_LETTER
}

fun requestLogin(
    userName: String,
    password: String,
    repository: AuthenticationRepository = authenticationRepository
) : Single<AuthenticationResponse> {
    return repository.takeIf { userName.isValidUserCredential() && password.isValidUserCredential() }
        ?.requestLogin(UserCredentials(userName, password))
        ?.flatMap { repository.saveToken(it) }
        ?.onErrorReturn { AuthenticationResponse(false, null, it.message?:it.toString()) }
        ?: Single.just(AuthenticationResponse(false, null, "invalid user name or password"))
}

fun requestRegister(
    userName: String,
    password: String,
    repository: AuthenticationRepository = authenticationRepository
) : Single<AuthenticationResponse> {
    return repository.takeIf { userName.isValidUserCredential() && password.isValidUserCredential() }
        ?.requestRegister(UserCredentials(userName, password))
        ?.flatMap { repository.saveToken(it) }
        ?: Single.just(AuthenticationResponse(false, null, "invalid user name or password"))
}

fun isLoggedInUser(repository: AuthenticationRepository = authenticationRepository) : Single<Boolean> {
    return repository.hasTokenSaved()
}