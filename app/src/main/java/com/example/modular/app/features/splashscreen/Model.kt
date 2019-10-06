package com.example.modular.app.features.splashscreen

import com.example.modular.domain.usecase.isLoggedInUser
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

fun model(
    intents: BehaviorSubject<Intens>,
    scheduler: Scheduler = Schedulers.io()
) : BehaviorSubject<ViewState> {

    val viewState = BehaviorSubject.create<ViewState>()

    intents.observeOn(scheduler).subscribeBy {
        when(it) {
            is Initialize -> waitThenFinishInitialization(viewState)
        }
    }

    return viewState
}

private fun waitThenFinishInitialization(viewStates: BehaviorSubject<ViewState>) {
    viewStates.onNext(OnInitializeStarted)
    Observable.interval(2, TimeUnit.SECONDS).firstElement().subscribeBy {
        isLoggedInUser()
            .map { OnInitializeFinished(it) }
            .blockingGet()
            .also { viewStates.onNext(it) }

    }
}