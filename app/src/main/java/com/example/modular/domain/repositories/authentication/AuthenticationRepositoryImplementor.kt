package com.example.modular.domain.repositories.authentication

import com.example.modular.domain.gateway.PreferredGateway
import com.example.modular.domain.gateway.ServerGateway
import com.example.modular.domain.gateway.preferenceGateway
import com.example.modular.domain.gateway.serverGateway
import com.example.modular.entities.AuthenticationResponse
import com.example.modular.entities.UserCredentials
import io.reactivex.Maybe
import io.reactivex.Single

private const val KEY_TOKEN = "KEY_TOKEN"
private const val DEFAULT_KEY_TOKEN = ""

class AuthenticationRepositoryImplementor(
    private val server: ServerGateway = serverGateway,
    private val preffered: PreferredGateway = preferenceGateway
) : AuthenticationRepository {

    override fun requestLogin(credentials: UserCredentials): Single<AuthenticationResponse> {
        return server.requestLogin(credentials.userName, credentials.password)
    }

    override fun requestRegister(credentials: UserCredentials): Single<AuthenticationResponse> {
        return server.requestRegister(credentials.userName, credentials.password)
    }

    override fun saveToken(authenticationResponse: AuthenticationResponse): Single<AuthenticationResponse> {
        return Single.fromCallable {
            authenticationResponse.token?.also { preffered.save(KEY_TOKEN, it) }
            authenticationResponse
        }
    }

    override fun hasTokenSaved(): Single<Boolean> {
        return preffered.isSaved(KEY_TOKEN)
    }

    override fun loadToken(): Maybe<String> {
        return preffered.load(KEY_TOKEN, DEFAULT_KEY_TOKEN).filter { it != DEFAULT_KEY_TOKEN }
    }

}