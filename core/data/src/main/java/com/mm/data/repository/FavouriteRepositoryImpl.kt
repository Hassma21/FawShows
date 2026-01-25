package com.mm.data.repository

import com.mm.database.FavouriteDao
import com.mm.database.entitiy.toFavouriteEntity
import com.mm.database.entitiy.toFavouriteModel
import com.mm.domain.model.Favourite
import com.mm.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val dao: FavouriteDao
) : FavouriteRepository {

    override suspend fun addFavourite(favourite: Favourite) {
        dao.insertFavourite(favourite.toFavouriteEntity())
    }

    override suspend fun removeFavourite(tmdbId: Long) {
        dao.deleteFavourite(tmdbId)
    }

    override fun getFavourites(): Flow<List<Favourite>> =
        dao.getFavourites().map { list -> list.map { it.toFavouriteModel() } }
}
