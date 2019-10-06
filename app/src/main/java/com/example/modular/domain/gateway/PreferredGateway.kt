package com.example.modular.domain.gateway

import android.content.Context
import android.content.SharedPreferences
import com.example.modular.domain.DomainIntegration
import io.reactivex.Completable
import io.reactivex.Single

val preferenceGateway by lazy { PreferredGateway() }

const val PREFERENCE_NAME = "PREFERENCE_NAME"

class PreferredGateway {

    inline fun <reified T: Any> save(key: String, value: T): Single<T> {
        return Single.fromCallable {
            DomainIntegration.getApplication()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .apply { putValue(key, value) }
                .apply()
            value
        }
    }

    inline fun <reified T: Any> load(key: String, defaultValue: T): Single<T> {
        return Single.fromCallable {
            DomainIntegration.getApplication()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .run { getValue(key, defaultValue) }
        }
    }

    fun isSaved(key: String): Single<Boolean> {
        return Single.fromCallable {
            DomainIntegration.getApplication()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .contains(key)
        }
    }

    fun remove(key: String): Completable {
        return Completable.fromAction {
            DomainIntegration.getApplication()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(key)
                .apply()
        }
    }
}

inline fun <reified T: Any> SharedPreferences.Editor.putValue(
    key : String,
    value : T
) {
    when(T::class) {
        Boolean::class -> putBoolean(key, value as Boolean)
        Int::class -> putInt(key, value as Int)
        Long::class -> putLong(key, value as Long)
        Float::class -> putFloat(key, value as Float)
        String::class -> putString(key, value as String)
        else -> throw UnsupportedOperationException(" Not support preference type")
    }
}

inline fun <reified T: Any> SharedPreferences.getValue(
    key: String,
    defaultValue: T
) : T {
    return when(T::class) {
        Boolean::class -> getBoolean(key, defaultValue as Boolean) as T
        Int::class -> getInt(key, defaultValue as Int) as T
        Long::class -> getLong(key, defaultValue as Long) as T
        Float::class -> getFloat(key, defaultValue as Float) as T
        String::class -> getString(key, defaultValue as String) as T
        else -> throw UnsupportedOperationException("Not support preferences type")
    }
}

