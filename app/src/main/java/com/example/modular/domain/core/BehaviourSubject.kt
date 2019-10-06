package com.example.modular.domain.core

class BehaviourSubject<T>(
    private var item: T? = null
) {
    private val observer = mutableListOf<(T) -> Unit>()

    fun subscribe(onNext: (T) -> Unit){
        item?.also{onNext(it)}
        observer.add(onNext)
    }

    fun onNext(item: T) {
        this.item = item
        observer.forEach { it.invoke(item) }
    }

    fun onComplete() {
        observer.clear()
        item = null
    }
}