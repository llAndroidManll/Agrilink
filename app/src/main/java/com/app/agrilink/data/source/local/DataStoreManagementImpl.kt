package com.app.agrilink.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.agrilink.domain.repository.IDataStoreManagement
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManagementImpl(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
) : IDataStoreManagement {

    companion object {
        private const val USER_DATA_KEY_VALUE = "UserDataKey"
        val USER_DATA_KEY = stringPreferencesKey(USER_DATA_KEY_VALUE)
    }

    override fun <T> getData(type: Class<T>, key: Preferences.Key<String>): Flow<T?> {
        return dataStore.data.map { preferences ->
            gson.fromJson(preferences[key], type)
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