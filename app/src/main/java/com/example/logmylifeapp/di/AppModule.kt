package com.example.logmylifeapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Extension property — DataStore is created once per app process
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// @Module tells Hilt "this object provides dependencies"
// @InstallIn(SingletonComponent) means they live as long as the app does
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // @Provides tells Hilt how to create a DataStore<Preferences>
    // @Singleton ensures only one instance is created app-wide
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
