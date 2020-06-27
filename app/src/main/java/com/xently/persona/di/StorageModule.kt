package com.xently.persona.di

import android.content.Context
import androidx.room.Room
import com.xently.persona.PersonaDatabase
import com.xently.persona.data.source.local.dao.PersonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun providePersonaDatabase(@ApplicationContext context: Context): PersonaDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PersonaDatabase::class.java,
            "persona.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePersonDao(db: PersonaDatabase): PersonDao = db.personDao
}