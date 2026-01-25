package com.mm.domain.repository

import com.mm.domain.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    suspend fun addFavourite(favourite: Favourite)
    suspend fun removeFavourite(tmdbId: Long)
    fun getFavourites(): Flow<List<Favourite>>
}
