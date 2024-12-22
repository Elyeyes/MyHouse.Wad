package com.mathieu_elyes.housewad.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull


private val Context.tokenStore by preferencesDataStore(name = "token")

class TokenStorage(private val context: Context) {
    private val token = stringPreferencesKey("token")

    suspend fun write(tokenToStore: String) {
        context.tokenStore.edit { preferences ->
            preferences[token] = tokenToStore
        }
    }

    suspend fun read(): String? {
        return context.tokenStore.data.firstOrNull()?.get(token)
    }

    suspend fun clear() {
        context.tokenStore.edit { preferences ->
            preferences.clear()
        }
    }
}