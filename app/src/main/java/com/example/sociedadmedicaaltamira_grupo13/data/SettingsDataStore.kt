package com.example.sociedadmedicaaltamira_grupo13.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore privado de este archivo
private val Context.dataStore by preferencesDataStore("settings")

// Sesión de usuario que guardaremos
data class UserSession(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val token: String
)

object SettingsKeys {
    // Modo especial
    val MODO_ESPECIAL = booleanPreferencesKey("modo_especial")

    // Sesión de usuario
    val USER_ID = longPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_ROLE = stringPreferencesKey("user_role")
    val USER_TOKEN = stringPreferencesKey("user_token")
}

class SettingsDataStore(private val context: Context) {

    // ------------- MODO ESPECIAL -------------
    val modoEspecial: Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[SettingsKeys.MODO_ESPECIAL] ?: false
        }

    suspend fun setModoEspecial(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.MODO_ESPECIAL] = enabled
        }
    }

    // ------------- SESIÓN DE USUARIO -------------
    val userSessionFlow: Flow<UserSession?> =
        context.dataStore.data.map { prefs ->
            val id = prefs[SettingsKeys.USER_ID] ?: return@map null
            val name = prefs[SettingsKeys.USER_NAME] ?: ""
            val email = prefs[SettingsKeys.USER_EMAIL] ?: ""
            val role = prefs[SettingsKeys.USER_ROLE] ?: ""
            val token = prefs[SettingsKeys.USER_TOKEN] ?: ""

            UserSession(
                id = id,
                name = name,
                email = email,
                role = role,
                token = token
            )
        }

    suspend fun saveUserSession(session: UserSession) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.USER_ID] = session.id
            prefs[SettingsKeys.USER_NAME] = session.name
            prefs[SettingsKeys.USER_EMAIL] = session.email
            prefs[SettingsKeys.USER_ROLE] = session.role
            prefs[SettingsKeys.USER_TOKEN] = session.token
        }
    }

    suspend fun clearUserSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(SettingsKeys.USER_ID)
            prefs.remove(SettingsKeys.USER_NAME)
            prefs.remove(SettingsKeys.USER_EMAIL)
            prefs.remove(SettingsKeys.USER_ROLE)
            prefs.remove(SettingsKeys.USER_TOKEN)
        }
    }
}
