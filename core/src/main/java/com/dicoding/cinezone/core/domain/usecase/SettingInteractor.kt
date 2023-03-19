package com.dicoding.cinezone.core.domain.usecase

import com.dicoding.cinezone.core.domain.repository.ISettingPreferences
import javax.inject.Inject

class SettingInteractor @Inject constructor(private val settingPreferences: ISettingPreferences) : SettingUseCase {

    override fun getThemeSetting() = settingPreferences.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) = settingPreferences.saveThemeSetting(isDarkModeActive)

}