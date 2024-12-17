package com.mathieu_elyes.housewad.Storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

private val Context.deviceStore by preferencesDataStore(name = "device")

class DeviceStorage {
    private lateinit var context : Context
    private val deviceId = stringPreferencesKey("device")
    constructor(context: Context){
        this.context = context
    }
    suspend fun write(deviceIdStore: String) {
        this.context.deviceStore.edit { preferences ->
            preferences[deviceId] = deviceIdStore
        }
    }

    suspend fun read(): String? {
        val data = context.deviceStore.data.firstOrNull()?.get(deviceId).toString()
        System.out.println("deviceId read=" + deviceId + "context "+ context)
        return data
    }

    suspend fun clear() {
        this.context.deviceStore.edit { preferences ->
            preferences.clear()
        }
    }
}
//
//class DeviceStorage(context: Context) {
//    private val deviceId = stringPreferencesKey("device")
//    private val deviceStore = context.deviceStore
//
//    suspend fun write(deviceIdStore: String) {
//        deviceStore.edit { preferences ->
//            preferences[deviceId] = deviceIdStore
//        }
//    }
//
//    suspend fun read(): String? {
//        System.out.println("deviceId read=" + deviceId + "context "+ this)
//        return deviceStore.data.firstOrNull()?.get(deviceId)
//
//    }
//    suspend fun clear() {
//        deviceStore.edit { preferences ->
//            preferences.clear()
//        }
//    }
//}