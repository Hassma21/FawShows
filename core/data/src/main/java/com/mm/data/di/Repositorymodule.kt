package com.mm.data.di

import com.mm.data.repository.FavouriteRepositoryImpl
import com.mm.data.repository.MediaRepositoryImpl
import com.mm.domain.repository.FavouriteRepository
import com.mm.domain.repository.MediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMediaRepository(
        impl: MediaRepositoryImpl
    ): MediaRepository

    @Binds
    @Singleton
    abstract fun bindFavouriteRepository(
        impl: FavouriteRepositoryImpl
    ): FavouriteRepository
}