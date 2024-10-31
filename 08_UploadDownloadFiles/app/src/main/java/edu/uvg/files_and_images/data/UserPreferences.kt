package edu.uvg.files_and_images.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension para obtener el DataStore
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val NAME_KEY = stringPreferencesKey("user_name")
        val SURNAME_KEY = stringPreferencesKey("user_surname")
        val EMAIL_KEY = stringPreferencesKey("user_email")
        val DOB_KEY = stringPreferencesKey("user_dob")
    }

    // Funciones para guardar datos
    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    suspend fun saveSurname(surname: String) {
        context.dataStore.edit { preferences ->
            preferences[SURNAME_KEY] = surname
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun saveDob(dob: String) {
        context.dataStore.edit { preferences ->
            preferences[DOB_KEY] = dob
        }
    }

    // Funciones para leer datos
    val nameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[NAME_KEY]
    }

    val surnameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SURNAME_KEY]
    }

    val emailFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[EMAIL_KEY]
    }

    val dobFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[DOB_KEY]
    }
}
