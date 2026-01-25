package com.mm.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mm.domain.model.Favourite

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey
    val tmdbId: Long,
    val title: String,
    val imagePath: String,
    val rating: Double,
    val mediaType: Int
)

fun FavouriteEntity.toFavouriteModel(): Favourite =
    Favourite(
        tmdbId = tmdbId,
        title = title,
        posterPath = imagePath,
        rating = rating,
        mediaType = mediaType
    )

fun Favourite.toFavouriteEntity(): FavouriteEntity =
    FavouriteEntity(
        tmdbId = tmdbId,
        title = title,
        imagePath = posterPath,
        rating = rating,
        mediaType = mediaType
    )

