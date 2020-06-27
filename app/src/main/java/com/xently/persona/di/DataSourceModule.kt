package com.xently.persona.di

import com.xently.persona.data.source.IPersonDataSource
import com.xently.persona.data.source.local.PersonLocalDataSource
import com.xently.persona.data.source.local.dao.PersonDao
import com.xently.persona.data.source.remote.PersonRemoteDataSource
import com.xently.persona.data.source.remote.services.PersonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    @LocalPersonDataSource
    fun providePersonLocalDataSource(dao: PersonDao): IPersonDataSource = PersonLocalDataSource(dao)

    @Provides
    @Singleton
    @RemotePersonDataSource
    fun providePersonRemoteDataSource(
        service: PersonService,
        ioDispatcher: CoroutineDispatcher
    ): IPersonDataSource = PersonRemoteDataSource(service, ioDispatcher)
}