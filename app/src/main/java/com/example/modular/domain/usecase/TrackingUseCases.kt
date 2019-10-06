package com.example.modular.domain.usecase

import com.example.modular.domain.repositories.analytics.AnalyticsRepository
import com.example.modular.domain.repositories.analytics.analyticsRepository
import com.example.modular.entities.AnalyticsEvent
import io.reactivex.Completable
import java.util.*

fun sessionStart(
    sessionName: String,
    repository: AnalyticsRepository = analyticsRepository
) : Completable {
    return repository.saveEvent(sessionName, AnalyticsEvent("session started", Date().time))
}

fun flushSession(
    sessionName: String,
    repository: AnalyticsRepository = analyticsRepository
) : Completable {
    return repository.loadEvent(sessionName).flatMapCompletable { repository.flustEvent(it) }
}