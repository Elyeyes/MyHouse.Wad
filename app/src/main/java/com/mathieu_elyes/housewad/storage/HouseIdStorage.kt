package com.mathieu_elyes.housewad.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

private val Context.houseIdStore by preferencesDataStore(name = "houseId")

class HouseIdStorage(private val context: Context) {
    private val houseId = stringPreferencesKey("houseId")

    suspend fun write(houseIdStore: String) {
        context.houseIdStore.edit { preferences ->
            preferences[houseId] = houseIdStore
        }
    }

    suspend fun read(): String? {
        return context.houseIdStore.data.firstOrNull()?.get(houseId)
    }

    suspend fun clear() {
        context.houseIdStore.edit { preferences ->
            preferences.clear()
        }
    }
}