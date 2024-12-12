package com.app.agrilink.data.repository

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.agrilink.R
import com.app.agrilink.domain.repository.IDataStoreManagement
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(R.string.app_preferences.toString())

class DataStoreManagementImpl<T>(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
    private val key: String = "key"
) : IDataStoreManagement<T> {


    override suspend fun saveData(obj: T) {
        dataStore.edit { pref ->
            pref[stringPreferencesKey(key)] = gson.toJson(obj)
        }
    }

    override fun getData(type: Class<out T>): Flow<T?> {
        return dataStore.data.map { preferences ->
            gson.fromJson(preferences[stringPreferencesKey(key)], type)
        }
    }

    override suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
        }
    }

}