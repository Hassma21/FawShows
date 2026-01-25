package com.mm.fawshows_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mm.ui.R
import com.mm.common.result.Result
import com.mm.domain.model.MediaDto
import com.mm.domain.use_case.GetBestMoviesUseCase
import com.mm.domain.use_case.GetBestTvsUseCase
import com.mm.domain.use_case.GetNowPlayingMoviesUseCase
import com.mm.domain.use_case.GetTrendingDailyMoviesUseCase
import com.mm.domain.use_case.GetTrendingDailyTvsUseCase
import com.mm.domain.use_case.GetTrendingWeeklyMoviesUseCase
import com.mm.domain.use_case.GetTrendingWeeklyTvsUseCase
import com.mm.domain.use_case.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FawMainViewModel @Inject constructor(
    private val getBestMovies: GetBestMoviesUseCase,
    private val getBestTvs: GetBestTvsUseCase,
    private val getTrendingDailyMovies: GetTrendingDailyMoviesUseCase,
    private val getTrendingWeeklyMovies: GetTrendingWeeklyMoviesUseCase,
    private val getTrendingDailyTvs: GetTrendingDailyTvsUseCase,
    private val getTrendingWeeklyTvs: GetTrendingWeeklyTvsUseCase,
    private val getNowPlayingMovies: GetNowPlayingMoviesUseCase,
    private val getUpcomingMovies: GetUpcomingMoviesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<FawMainUiState>(FawMainUiState.Sections())
    val uiState: StateFlow<FawMainUiState> = _uiState.asStateFlow()

    private var lastSections: FawMainUiState.Sections? = null

    init {
        loadAll()
    }

    private fun loadAll() {
        loadBestMovies()
        loadBestTvs()
        loadTrendingDailyMovies()
        loadTrendingWeeklyMovies()
        loadTrendingDailyTvs()
        loadTrendingWeeklyTvs()
        loadNowPlaying()
        loadUpcoming()
    }

    fun loadBestMovies() = loadSection({ copy(bestMovies = it) }) { getBestMovies() }
    fun loadBestTvs() = loadSection({ copy(bestTvs = it) }) { getBestTvs() }
    fun loadTrendingDailyMovies() = loadSection({ copy(trendingDailyMovies = it) }) { getTrendingDailyMovies() }
    fun loadTrendingWeeklyMovies() = loadSection({ copy(trendingWeeklyMovies = it) }) { getTrendingWeeklyMovies() }
    fun loadTrendingDailyTvs() = loadSection({ copy(trendingDailyTvs = it) }) { getTrendingDailyTvs() }
    fun loadTrendingWeeklyTvs() = loadSection({ copy(trendingWeeklyTvs = it) }) { getTrendingWeeklyTvs() }
    fun loadNowPlaying() = loadSection({ copy(nowPlayingMovies = it) }) { getNowPlayingMovies() }
    fun loadUpcoming() = loadSection({ copy(upcomingMovies = it) }) { getUpcomingMovies() }

    private fun loadSection(
        setter: FawMainUiState.Sections.(SectionUiState<MediaDto>) -> FawMainUiState.Sections,
        loader: () -> Flow<Result<List<MediaDto>>>
    ) {
        viewModelScope.launch {
            loader().collect { result ->
                _uiState.update { state ->
                    val sections = state as? FawMainUiState.Sections ?: FawMainUiState.Sections()
                    val sectionState = when (result) {
                        is Result.Loading -> SectionUiState.Loading
                        is Result.Success -> SectionUiState.Success(result.data)
                        is Result.Error -> SectionUiState.Error(R.string.network_error)
                    }
                    sections.setter(sectionState)
                }
            }
        }
    }

    fun showAllMedia(title: String, items: List<MediaDto>) {
        val current = _uiState.value
        if (current is FawMainUiState.Sections) lastSections = current
        _uiState.value = FawMainUiState.MediaList(title, items)
    }

    fun onBackPressed() {
        _uiState.value = lastSections ?: FawMainUiState.Sections()
    }
}

sealed interface SectionUiState<out T> {
    data object Loading : SectionUiState<Nothing>
    data class Success<T>(val data: List<T>) : SectionUiState<T>
    data class Error(val messageId: Int) : SectionUiState<Nothing>
}

sealed interface FawMainUiState {
    data class Sections(
        val bestMovies: SectionUiState<MediaDto> = SectionUiState.Loading,
        val bestTvs: SectionUiState<MediaDto> = SectionUiState.Loading,
        val trendingDailyMovies: SectionUiState<MediaDto> = SectionUiState.Loading,
        val trendingWeeklyMovies: SectionUiState<MediaDto> = SectionUiState.Loading,
        val trendingDailyTvs: SectionUiState<MediaDto> = SectionUiState.Loading,
        val trendingWeeklyTvs: SectionUiState<MediaDto> = SectionUiState.Loading,
        val nowPlayingMovies: SectionUiState<MediaDto> = SectionUiState.Loading,
        val upcomingMovies: SectionUiState<MediaDto> = SectionUiState.Loading
    ) : FawMainUiState

    data class MediaList(
        val title: String,
        val items: List<MediaDto>
    ) : FawMainUiState
}
