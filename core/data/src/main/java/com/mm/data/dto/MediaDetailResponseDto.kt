package com.mm.data.dto

import com.mm.domain.model.Cast
import com.mm.domain.model.Genre
import com.mm.domain.model.MediaDetail
import com.mm.domain.model.Trailer

data class MediaDetailResponseDto(
    val id: Long,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val genres: List<TmdbGenreDto>,
    val trailer: TmdbVideoDto?,
    val cast: List<CastDto>
)


data class TmdbGenreDto(
    val id: Int,
    val name: String
)

data class TmdbVideoDto(
    val id: String,
    val key: String,
    val site: String,
    val type: String
)

data class CastDto(
    val id: Int,
    val name: String,
    val character: String?,
    val profileUrl: String?
)


fun MediaDetailResponseDto.toMediaDetail(): MediaDetail =
    MediaDetail(
        mediaId = id,
        mediaTitle = title,
        mediaOverview = overview,
        mediaPosterPath = posterPath,
        mediaReleaseDate = releaseDate,
        mediaVoteAverage = voteAverage,
        mediaGenres = genres.map { it.toDomain() },
        mediaTrailer = trailer?.toDomain(),
        mediaCast = cast.map { it.toDomain() }
    )

fun TmdbGenreDto.toDomain(): Genre =
    Genre(
        id = id,
        name = name
    )

fun TmdbVideoDto.toDomain(): Trailer =
    Trailer(
        id = id,
        youtubeKey = key
    )

fun CastDto.toDomain(): Cast =
    Cast(
        id = id,
        name = name,
        character = character,
        profileUrl = profileUrl
    )
