package com.app.agrilink.domain.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

interface IDataStoreManagement {
    companion object {
        private const val USER_DATA_KEY_VALUE = "UserDataKey"
        val USER_DATA_KEY = stringPreferencesKey(USER_DATA_KEY_VALUE)

        //Avelacnum es keyer urish objectneri hamar ete anhrajesht a
    }
    fun <T> getData(type: Class<T>, key: Preferences.Key<String>): Flow<T?>
    suspend fun <T> saveData(obj: T, key: Preferences.Key<String>): Boolean
    suspend fun <T> clearData(key: Preferences.Key<T>): Boolean
    suspend fun clearDataStore(): Boolean
}