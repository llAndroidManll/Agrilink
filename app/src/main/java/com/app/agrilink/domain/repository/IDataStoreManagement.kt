package com.app.agrilink.domain.repository

import kotlinx.coroutines.flow.Flow

interface IDataStoreManagement<T> {
    suspend fun saveData(obj: T)
    fun getData(type: Class<out T>): Flow<T?>
    suspend fun clearData()
}