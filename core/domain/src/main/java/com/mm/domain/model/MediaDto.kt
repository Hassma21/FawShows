package com.mm.domain.model

data class MediaDto (
    val mediaId: Long,
    val mediaTitle: String,
    val mediaPosterPath: String?,
    val mediaVotAvarage: Double?,
    val mediaType: MediaType
)
enum class MediaType {
    Movie,
    Tv
}

