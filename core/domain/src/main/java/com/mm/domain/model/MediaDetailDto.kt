package com.mm.domain.model

data class MediaDetail(
    val mediaId: Long,
    val mediaTitle: String,
    val mediaOverview: String?,
    val mediaPosterPath: String?,
    val mediaReleaseDate: String?,
    val mediaVoteAverage: Double?,
    val mediaGenres: List<Genre>,
    val mediaTrailer: Trailer?,
    val mediaCast: List<Cast>
)

data class Genre(
    val id: Int,
    val name: String
)

data class Trailer(
    val id: String,
    val youtubeKey: String
)

data class Cast(
    val id: Int,
    val name: String,
    val character: String?,
    val profileUrl: String?
)
