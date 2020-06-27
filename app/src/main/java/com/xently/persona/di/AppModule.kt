package com.xently.persona.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideCoroutineIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}