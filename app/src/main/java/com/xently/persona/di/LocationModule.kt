package com.xently.persona.di

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(FragmentComponent::class)
object LocationModule {
    @Provides
    fun provideGeocoder(@ActivityContext context: Context): Geocoder = Geocoder(context)
}