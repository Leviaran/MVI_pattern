package com.example.modular.domain.repositories.analytics

import com.example.modular.entities.AnalyticsEvent
import io.reactivex.Completable
import io.reactivex.Maybe

val analyticsRepository by lazy { AnalyticRepositoryImplementer() }

interface AnalyticsRepository {

    fun saveEvent(key: Any, event : AnalyticsEvent) : Completable

    fun loadEvent(key: Any): Maybe<AnalyticsEvent>

    fun flustEvent(event: AnalyticsEvent): Completable
}