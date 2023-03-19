package com.dicoding.cinezone.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dicoding.cinezone.core.data.SettingPreferences
import com.dicoding.cinezone.core.domain.repository.ISettingPreferences
import com.dicoding.cinezone.core.utils.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideIDataStore(dataStore: DataStore<Preferences>): ISettingPreferences {
        return SettingPreferences(dataStore)
    }

}