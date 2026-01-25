package com.mm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mm.database.entitiy.FavouriteEntity

@Database(
    entities = [FavouriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavouriteDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao
}
