package com.mm.data.dto

import com.mm.domain.model.MediaDto
import com.mm.domain.model.MediaType

data class MovieResponseDto(
    val id: Long,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val genreIds: List<Int>,
    val releaseDate: String?
)
fun MovieResponseDto.toDomain(): MediaDto =
    MediaDto(
        mediaId = id,
        mediaTitle = title,
        mediaPosterPath = posterPath,
        mediaVotAvarage = voteAverage,
        mediaType = MediaType.Movie
    )

fun List<MovieResponseDto>.toMovieDomainList(): List<MediaDto> =
    map { it.toDomain() }
