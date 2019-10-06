package com.example.modular.app.features.splashscreen

sealed class Intens
object Initialize : Intens()

sealed class ViewState
object OnInitializeStarted : ViewState()
class OnInitializeFinished(val loggedInUser: Boolean) : ViewState()