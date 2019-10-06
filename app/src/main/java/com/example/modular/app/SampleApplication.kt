package com.example.modular.app

import android.app.Application
import com.example.modular.domain.DomainIntegration
import com.example.modular.domain.usecase.flushSession
import com.example.modular.domain.usecase.sessionStart
import io.reactivex.schedulers.Schedulers

private const val SESSION_APP = "SESSION_APP"

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DomainIntegration.with(this)

        sessionStart(SESSION_APP)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnError(Throwable::printStackTrace)
            .onErrorComplete()
            .subscribe()

    }

    override fun onTerminate() {
        flushSession(SESSION_APP)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnError(Throwable::printStackTrace)
            .onErrorComplete()
            .subscribe()
        super.onTerminate()
    }
}