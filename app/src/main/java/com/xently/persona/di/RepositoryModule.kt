package com.xently.persona.di

import com.xently.persona.data.repository.person.IPersonRepository
import com.xently.persona.data.repository.person.PersonRepository
import com.xently.persona.data.source.IPersonDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePersonRepository(
        @LocalPersonDataSource local: IPersonDataSource,
        @RemotePersonDataSource remote: IPersonDataSource,
        ioDispatcher: CoroutineDispatcher
    ): IPersonRepository = PersonRepository(local, remote, ioDispatcher)
}