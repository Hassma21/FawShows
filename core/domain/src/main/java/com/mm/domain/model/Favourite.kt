package com.mm.domain.model

data class Favourite(
    val tmdbId: Long,
    val title: String,
    val posterPath: String,
    val rating: Double,
    val mediaType: Int
)


