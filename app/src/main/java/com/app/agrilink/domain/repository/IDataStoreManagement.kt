package com.app.agrilink.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface IDataStoreManagement {
    fun <T> getData(type: Class<T>, key: Preferences.Key<String>): Flow<T?>
    suspend fun <T> saveData(obj: T, key: Preferences.Key<String>): Boolean
    suspend fun <T> clearData(key: Preferences.Key<T>): Boolean
    suspend fun clearDataStore(): Boolean
}