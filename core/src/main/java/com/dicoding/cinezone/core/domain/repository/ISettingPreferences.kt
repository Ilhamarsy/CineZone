package com.dicoding.cinezone.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ISettingPreferences {

    fun getThemeSetting(): Flow<Boolean>

    suspend fun saveThemeSetting(isDarkModeActive: Boolean)

}