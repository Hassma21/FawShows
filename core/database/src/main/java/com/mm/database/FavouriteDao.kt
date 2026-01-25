package com.mm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mm.database.entitiy.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(favourite: FavouriteEntity)

    @Query("SELECT * FROM favourites")
    fun getFavourites(): Flow<List<FavouriteEntity>>

    @Query("DELETE FROM favourites WHERE tmdbId = :tmdbId")
    suspend fun deleteFavourite(tmdbId: Long)
}
