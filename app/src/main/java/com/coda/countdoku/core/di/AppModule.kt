package com.coda.countdoku.core.di

import android.content.Context
import com.coda.countdoku.data.local.dao.GameMetaDataDao
import com.coda.countdoku.data.local.dao.LevelDao
import com.coda.countdoku.data.preferences.PlayerProgressManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providePlayerProgressManager(context: Context): PlayerProgressManager {
        return PlayerProgressManager(context)
    }

    @Provides
    @Singleton
    fun provideGameMetaDataDao(context: Context): GameMetaDataDao {
        return GameMetaDataDao(context)
    }

    @Provides
    @Singleton
    fun provideLevelDao(context: Context): LevelDao {
        return LevelDao(context)
    }
}