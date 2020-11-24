package com.dvidal.samplearticles.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class ViewModelTestModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(): ViewModelProvider.Factory = mockk()
}