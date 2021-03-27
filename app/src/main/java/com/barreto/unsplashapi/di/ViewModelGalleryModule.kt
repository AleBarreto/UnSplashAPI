package com.barreto.unsplashapi.di

import com.barreto.unsplashapi.api.UnSplashApi
import com.barreto.unsplashapi.api.repository.UnSplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelGalleryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(unSplashApi: UnSplashApi) = UnSplashRepository(unSplashApi)

}