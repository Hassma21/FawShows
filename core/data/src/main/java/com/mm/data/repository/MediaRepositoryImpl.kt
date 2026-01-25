package com.mm.data.repository

import com.mm.data.MediaApi
import com.mm.data.dto.toDomain
import com.mm.data.dto.toMediaDetail
import com.mm.data.dto.toMovieDomainList
import com.mm.data.dto.toTvDomainList
import com.mm.domain.model.MediaDetail
import com.mm.domain.model.MediaDto
import com.mm.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepositoryImpl @Inject constructor(private val mediaApi: MediaApi): MediaRepository {
    override suspend fun getBestMovies(): List<MediaDto> {
        return mediaApi.getBestMovies().toMovieDomainList()
    }

    override suspend fun getBestTvs(): List<MediaDto> {
        return mediaApi.getBestTvShows().toTvDomainList()
    }

    override suspend fun getTrendingDailyMovies(): List<MediaDto> {
        return mediaApi.getTrendingDailyMovies().toMovieDomainList()
    }

    override suspend fun getTrendingWeeklyMovies(): List<MediaDto> {
        return mediaApi.getTrendingWeeklyMovies().toMovieDomainList()
    }

    override suspend fun getTrendingDailyTvs(): List<MediaDto> {
        return  mediaApi.getTrendingDailyTv().toTvDomainList()
    }

    override suspend fun getTrendingWeeklyTvs(): List<MediaDto> {
        return mediaApi.getTrendingWeeklyTv().toTvDomainList()
    }

    override suspend fun getNowPlayingMovies(): List<MediaDto> {
        return mediaApi.getNowPlayingMovies().toMovieDomainList()
    }

    override suspend fun getUpcomingMovies(): List<MediaDto> {
        return mediaApi.getUpcomingMovies().toMovieDomainList()
    }

    override suspend fun getMovieDetail(
        tmdbId: Long,
        language: String
    ): MediaDetail {
        return mediaApi.getMovieDetail(tmdbId,language).toMediaDetail()
    }

    override suspend fun getTvDetail(
        tmdbId: Long,
        language: String
    ): MediaDetail {
        return mediaApi.getTvDetail(tmdbId,language).toMediaDetail()
    }

    override suspend fun searchMedia(query: String): List<MediaDto> {
        return mediaApi
            .searchMedia(query)
            .map { it.toDomain() }
    }

}