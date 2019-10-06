package com.example.modular.app.features.splashscreen

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

fun check() {

}

fun SplashFragment.view(viewState: BehaviorSubject<ViewState>): Disposable =
    viewState.observeOn(AndroidSchedulers.mainThread()).subscribeBy {
        when(it) {
            is OnInitializeStarted -> showProgress()
            is OnInitializeFinished -> hideProgressAndNavigate(it.loggedInUser)
        }
    }

private fun SplashFragment.hideProgressAndNavigate(loggedInUser: Boolean) {

    progressBar.visibility = View.GONE
    if (loggedInUser) startActivity(Intent(context, HomeActivity::class.java))
    else startActivity(Intent(context, LoginActivity::class.java))

    activity?.finish()
}

private fun SplashFragment.showProgress() {
    progressBar.visibility = View.VISIBLE
}