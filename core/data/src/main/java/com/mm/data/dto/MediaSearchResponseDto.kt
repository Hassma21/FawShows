package com.mm.data.dto

import com.mm.domain.model.MediaDto
import com.mm.domain.model.MediaType

data class MediaSearchResponseDto(
    val tmdbId: Long,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double?,
    val mediaType: String
)
fun MediaSearchResponseDto.toDomain() = MediaDto(
    mediaId = tmdbId,
    mediaTitle = title,
    mediaPosterPath = posterPath,
    mediaVotAvarage = voteAverage,
    mediaType = when (mediaType.uppercase()) {
        "MOVIE" -> MediaType.Movie
        "TV" -> MediaType.Tv
        else -> throw IllegalStateException("Unknown mediaType: $mediaType")
    }
)


