package com.example.modular.domain.repositories.authentication

import com.example.modular.entities.AuthenticationResponse
import com.example.modular.entities.UserCredentials
import io.reactivex.Maybe
import io.reactivex.Single

val authenticationRepository by lazy { AuthenticationRepositoryImplementor() }

interface AuthenticationRepository {

    fun requestLogin(credentials: UserCredentials): Single<AuthenticationResponse>

    fun requestRegister(credentials: UserCredentials) : Single<AuthenticationResponse>

    fun saveToken(authenticationResponse: AuthenticationResponse) : Single<AuthenticationResponse>

    fun hasTokenSaved() : Single<Boolean>

    fun loadToken(): Maybe<String>
}