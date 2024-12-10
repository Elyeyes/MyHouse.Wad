package com.mathieu_elyes.housewad

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull


private val Context.tokenStore by preferencesDataStore(name = "token")

class TokenStorage {
    private lateinit var context : Context
    private var token = stringPreferencesKey("token")
    constructor(context: Context){
        this.context = context
    }
    suspend fun write(tokenToStore : String){
        this.context.tokenStore.edit { preferences ->
            preferences[token] = tokenToStore
        }
    }
    suspend fun read(): String? {
        val data = context.tokenStore.data.firstOrNull()?.get(token).toString()
        return data
    }
}