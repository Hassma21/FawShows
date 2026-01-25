package com.mm.domain.repository

import com.mm.domain.model.MediaDetail
import com.mm.domain.model.MediaDto


interface MediaRepository {
    suspend fun getBestMovies() : List<MediaDto>
    suspend fun getBestTvs() : List<MediaDto>

    suspend fun getTrendingDailyMovies() : List<MediaDto>
    suspend fun getTrendingWeeklyMovies() : List<MediaDto>

    suspend fun getTrendingDailyTvs() : List<MediaDto>
    suspend fun getTrendingWeeklyTvs() : List<MediaDto>

    suspend fun getNowPlayingMovies() : List<MediaDto>
    suspend fun getUpcomingMovies() : List<MediaDto>

    suspend fun getMovieDetail(tmdbId: Long, language: String) : MediaDetail

    suspend fun getTvDetail(tmdbId: Long, language: String) : MediaDetail

    suspend fun searchMedia(query: String) : List<MediaDto>
}