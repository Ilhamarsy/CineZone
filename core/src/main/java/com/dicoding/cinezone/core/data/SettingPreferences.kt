package com.dicoding.cinezone.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.dicoding.cinezone.core.domain.repository.ISettingPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val themeKeyName = "theme_setting"

class SettingPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ISettingPreferences {

    private val themeKey = booleanPreferencesKey(themeKeyName)

    override fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

}
