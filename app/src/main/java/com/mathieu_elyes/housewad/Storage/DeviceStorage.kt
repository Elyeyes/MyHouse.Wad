package com.mathieu_elyes.housewad.Storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

private val Context.deviceStore by preferencesDataStore(name = "device")

class DeviceStorage(private val context: Context) {
    private val deviceId = stringPreferencesKey("device")

    suspend fun write(deviceIdStore: String) {
        context.deviceStore.edit { preferences ->
            preferences[deviceId] = deviceIdStore
        }
    }

    suspend fun read(): String? {
        return context.deviceStore.data.firstOrNull()?.get(deviceId)
    }

    suspend fun clear() {
        context.deviceStore.edit { preferences ->
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