package com.mm.database.di

import android.content.Context
import androidx.room.Room
import com.mm.database.FavouriteDao
import com.mm.database.FavouriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FavouriteDatabase =
        Room.databaseBuilder(
            context,
            FavouriteDatabase::class.java,
            "favourites.db"
        ).build()

    @Provides
    fun provideFavouriteDao(db: FavouriteDatabase): FavouriteDao =
        db.favouriteDao()
}
