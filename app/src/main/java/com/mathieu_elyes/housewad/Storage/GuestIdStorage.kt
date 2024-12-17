package com.mathieu_elyes.housewad.Storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull


private val Context.guestStore by preferencesDataStore(name = "guest")

class GuestIdStorage {
    private lateinit var context : Context
    private var guest = stringPreferencesKey("guest")
    constructor(context: Context){
        this.context = context
    }
    suspend fun write(guestToStore : String){
        this.context.guestStore.edit { preferences ->
            preferences[guest] = guestToStore
        }
    }
    suspend fun read(): String? {
        val data = context.guestStore.data.firstOrNull()?.get(guest).toString()
        System.out.println("guest read=" + guest + "context "+ context)
        return data
    }
}