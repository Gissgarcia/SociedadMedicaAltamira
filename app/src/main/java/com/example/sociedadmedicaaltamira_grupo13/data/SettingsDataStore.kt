package com.example.sociedadmedicaaltamira_grupo13.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private val Context.dataStore by preferencesDataStore("settings")

object SettingsKeys {
    val MODO_ESPECIAL = booleanPreferencesKey("modo_especial")
}

class SettingsDataStore(private val context: Context) {
    val modoEspecial: Flow<Boolean> =
        context.dataStore.data.map { it[SettingsKeys.MODO_ESPECIAL] ?: false }

    suspend fun setModoEspecial(enabled: Boolean) {
        context.dataStore.edit { it[SettingsKeys.MODO_ESPECIAL] = enabled }
    }
}
