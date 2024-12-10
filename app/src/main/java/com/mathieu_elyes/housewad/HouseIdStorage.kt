package com.mathieu_elyes.housewad

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

private val Context.houseIdStore by preferencesDataStore(name = "houseId")

class HouseIdStorage {
    private lateinit var context : Context
    private var houseId = stringPreferencesKey("houseId")
    constructor(context: Context){
        this.context = context
    }
    suspend fun write(tokenToStore : String){
        this.context.houseIdStore.edit { preferences ->
            preferences[houseId] = tokenToStore
        }
    }
    suspend fun read(): String? {
        val data = context.houseIdStore.data.firstOrNull()?.get(houseId).toString()
        return data
    }
}