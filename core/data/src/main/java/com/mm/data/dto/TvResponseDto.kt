package com.mm.data.dto

import com.mm.domain.model.MediaDto
import com.mm.domain.model.MediaType

data class TvResponseDto(
    val id: Long,
    val name: String,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val genreIds: List<Int>,
    val firstAirDate: String?
)
fun TvResponseDto.toDomain(): MediaDto =
    MediaDto(
        mediaId = id,
        mediaTitle = name,
        mediaPosterPath = posterPath,
        mediaVotAvarage = voteAverage,
        mediaType = MediaType.Tv
    )

fun List<TvResponseDto>.toTvDomainList(): List<MediaDto> =
    map { it.toDomain() }