package com.example.sociedadmedicaaltamira_grupo13.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Delegate para obtener el DataStore desde cualquier Context
val Context.settingsDataStore by preferencesDataStore(name = "settings")


data class UserSession(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val token: String
)

class SettingsDataStore(private val context: Context) {

    private val dataStore = context.settingsDataStore

    // Keys de Preferences
    private object Keys {
        val USER_ID: Preferences.Key<Long> = longPreferencesKey("user_id")
        val USER_NAME: Preferences.Key<String> = stringPreferencesKey("user_name")
        val USER_EMAIL: Preferences.Key<String> = stringPreferencesKey("user_email")
        val USER_ROLE: Preferences.Key<String> = stringPreferencesKey("user_role")
        val USER_TOKEN: Preferences.Key<String> = stringPreferencesKey("user_token")
    }


    val userSessionFlow: Flow<UserSession?> = dataStore.data.map { prefs ->
        val id = prefs[Keys.USER_ID] ?: return@map null
        val name = prefs[Keys.USER_NAME] ?: ""
        val email = prefs[Keys.USER_EMAIL] ?: ""
        val role = prefs[Keys.USER_ROLE] ?: ""
        val token = prefs[Keys.USER_TOKEN] ?: ""

        UserSession(
            id = id,
            name = name,
            email = email,
            role = role,
            token = token
        )
    }


    suspend fun saveUserSession(session: UserSession) {
        dataStore.edit { prefs ->
            prefs[Keys.USER_ID] = session.id
            prefs[Keys.USER_NAME] = session.name
            prefs[Keys.USER_EMAIL] = session.email
            prefs[Keys.USER_ROLE] = session.role
            prefs[Keys.USER_TOKEN] = session.token
        }
    }


    suspend fun clearUserSession() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.USER_ID)
            prefs.remove(Keys.USER_NAME)
            prefs.remove(Keys.USER_EMAIL)
            prefs.remove(Keys.USER_ROLE)
            prefs.remove(Keys.USER_TOKEN)
        }
    }
}
