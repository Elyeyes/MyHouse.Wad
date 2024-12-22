package com.mathieu_elyes.housewad.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.mathieu_elyes.housewad.datamodel.ModeData
import kotlinx.coroutines.flow.firstOrNull

private val Context.modeStore by preferencesDataStore(name = "mode")

class ModeStorage(private val context: Context) {
    private val mode = stringPreferencesKey("mode")
    private val gson = Gson()

    suspend fun write(modeToStore: ModeData) {
        val modeJson = gson.toJson(modeToStore)
        context.modeStore.edit { preferences ->
            preferences[mode] = modeJson
        }
    }

    suspend fun read(): ModeData? {
        val modeJson = context.modeStore.data.firstOrNull()?.get(mode)
        return modeJson.let { gson.fromJson(it, ModeData::class.java) }
    }

    suspend fun remove(modeToRemove: String) {
        val currentModes = read() ?: return
        val updatedModes = currentModes.deviceSetupList.filterNot { mode.name == modeToRemove }
        val newModeData = ModeData(currentModes.name, ArrayList(updatedModes))
        System.out.println("REMOVED $currentModes \n $updatedModes \n  $newModeData")
        write(newModeData)
    }
}