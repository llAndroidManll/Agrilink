package com.app.agrilink.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.app.agrilink.domain.preferences.IDataStoreManagement
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManagementImpl(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
) : IDataStoreManagement {

    override fun <T> getData(type: Class<T>, key: Preferences.Key<String>): Flow<T?> {
        return dataStore.data.map { preferences ->
            val json = preferences[key]
            if (json.isNullOrEmpty()) {
                null
            } else {
                try {
                    gson.fromJson(json, type)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    override suspend fun <T> saveData(obj: T, key: Preferences.Key<String>): Boolean {
        return kotlin.runCatching {
            dataStore.edit { pref ->
                pref[key] = gson.toJson(obj)
            }
        }.isSuccess
    }

    override suspend fun <T> clearData(key: Preferences.Key<T>): Boolean {
        return kotlin.runCatching {
            dataStore.edit { preferences ->
                preferences.remove(key)
            }
        }.isSuccess
    }

    override suspend fun clearDataStore(): Boolean {
        return kotlin.runCatching {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        }.isSuccess
    }
}