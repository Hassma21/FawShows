package com.mm.data

import com.mm.data.dto.MediaDetailResponseDto
import com.mm.data.dto.MediaSearchResponseDto
import com.mm.data.dto.MovieResponseDto
import com.mm.data.dto.TvResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MediaApi {


    @GET("api/media/movies/trending/daily")
    suspend fun getTrendingDailyMovies(): List<MovieResponseDto>

    @GET("api/media/movies/trending/weekly")
    suspend fun getTrendingWeeklyMovies(): List<MovieResponseDto>

    @GET("api/media/movies/now-playing")
    suspend fun getNowPlayingMovies(): List<MovieResponseDto>

    @GET("api/media/movies/upcoming")
    suspend fun getUpcomingMovies(): List<MovieResponseDto>

    @GET("api/media/movies/best")
    suspend fun getBestMovies(): List<MovieResponseDto>



    @GET("api/media/tv/trending/daily")
    suspend fun getTrendingDailyTv(): List<TvResponseDto>

    @GET("api/media/tv/trending/weekly")
    suspend fun getTrendingWeeklyTv(): List<TvResponseDto>

    @GET("api/media/tv/best")
    suspend fun getBestTvShows(): List<TvResponseDto>

    @GET("api/media/movie/detail")
    suspend fun getMovieDetail(
        @Query("tmdbId") tmdbId: Long,
        @Header("Accept-Language") language: String? = "tr"
    ): MediaDetailResponseDto

    @GET("api/media/tv/detail")
    suspend fun getTvDetail(
        @Query("tmdbId") tmdbId: Long,
        @Header("Accept-Language") language: String? = "tr"
    ): MediaDetailResponseDto

    @GET("api/media/search")
    suspend fun searchMedia(
        @Query("query") query: String
    ): List<MediaSearchResponseDto>
}
