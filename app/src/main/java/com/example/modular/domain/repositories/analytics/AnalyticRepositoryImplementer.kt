package com.example.modular.domain.repositories.analytics

import com.example.modular.domain.gateway.CacheGateway
import com.example.modular.domain.gateway.cacheGateWay
import com.example.modular.entities.AnalyticsEvent
import io.reactivex.Completable
import io.reactivex.Maybe

class AnalyticRepositoryImplementer(
    private val cache : CacheGateway = cacheGateWay
) : AnalyticsRepository {
    override fun saveEvent(key: Any, event: AnalyticsEvent): Completable {
        return cache.save(key, event).ignoreElement()
    }

    override fun loadEvent(key: Any): Maybe<AnalyticsEvent> {
        return cache.load(key)
    }

    override fun flustEvent(event: AnalyticsEvent): Completable {
        return Completable.fromAction{
            Thread.sleep(2000)
        }
    }

}