package com.example.modular.app.features.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.modular.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class SplashFragment : Fragment() {

    private val disposable = CompositeDisposable()
    private val intents : BehaviorSubject<Intens> = BehaviorSubject.createDefault(Initialize)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable.add(
            view(model(intents))
        )
    }

    override fun onDestroy() {
        intents.onComplete()
        disposable.clear()
        super.onDestroy()

    }
}