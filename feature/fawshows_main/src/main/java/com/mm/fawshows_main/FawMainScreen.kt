package com.mm.fawshows_main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mm.domain.model.MediaDto
import com.mm.domain.model.MediaType
import com.mm.fawshows.feature.main.R
import com.mm.ui.componenets.ErrorContent
import com.mm.ui.componenets.LoadingContent
import com.mm.ui.componenets.MediaCard
import com.mm.ui.componenets.SectionHeader
import com.mm.ui.state.TopAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FawMainScreen(
    viewModel: FawMainViewModel = hiltViewModel(),
    onItemClick: (Long, MediaType) -> Unit,
    onAppBarChange: (TopAppBarState) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val topAppBarState = when (uiState) {
        is FawMainUiState.Sections -> {
            TopAppBarState(visible = false)
        }

        is FawMainUiState.MediaList -> {
            val state = uiState as FawMainUiState.MediaList
            TopAppBarState(
                visible = true,
                title = state.title,
                showBack = true,
                onBackClick = viewModel::onBackPressed
            )
        }
    }
    LaunchedEffect(topAppBarState) {
        onAppBarChange(topAppBarState)
    }

    when (uiState) {
        is FawMainUiState.Sections -> {
            val sections = uiState as FawMainUiState.Sections
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {

                item {
                    val title = stringResource(R.string.section_best_movies)
                    MediaSection(
                        title = stringResource(R.string.section_best_movies),
                        section = sections.bestMovies,
                        onItemClick = onItemClick,
                        onRetry = viewModel::loadBestMovies,
                        onSeeAllClick = {
                            (sections.bestMovies as? SectionUiState.Success)?.let {
                                viewModel.showAllMedia(
                                    title,
                                    it.data
                                )
                            }
                        }
                    )
                }

                item {
                    val title = stringResource(R.string.section_best_tvs)
                    MediaSection(
                        title = title,
                        section = sections.bestTvs,
                        onItemClick = onItemClick,
                        onRetry = viewModel::loadBestTvs,
                        onSeeAllClick = {
                            (sections.bestTvs as? SectionUiState.Success)?.let {
                                viewModel.showAllMedia(
                                    title,
                                    it.data
                                )
                            }
                        }
                    )
                }

                item {
                    val title = stringResource(R.string.section_trending_daily_movies)
                    MediaSection(
                        title = title,
                        section = sections.trendingDailyMovies,
                        onItemClick = onItemClick,
                        onRetry = viewModel::loadTrendingDailyMovies,
                        onSeeAllClick = {
                            (sections.trendingDailyMovies as? SectionUiState.Success)?.let {
                                viewModel.showAllMedia(
                                    title,
                                    it.data
                                )
                            }
                        }
                    )
                }

                item {
                    val title = stringResource(R.string.section_trending_weekly_movies)
                    MediaSection(
                        title = title,
                        section = sections.trendingWeeklyMovies,
                        onItemClick = onItemClick,
                        onRetry = viewModel::loadTrendingWeeklyMovies,
                        onSeeAllClick = {
                            (sections.trendingWeeklyMovies as? SectionUiState.Success)?.let {
                                viewModel.showAllMedia(
                                    title,
                                    it.data
                                )
                            }
                        }
                    )
                }

                item {
                    val title = stringResource(R.string.section_trending_daily_tvs)
                    MediaSection(
                        title = title,
                        section = sections.trendingDailyTvs,
                        onItemClick = onItemClick,
                        onRetry = viewModel::loadTrendingDailyTvs,
                        onSeeAllClick = {
                            (sections.trendingDailyTvs as? SectionUiState.Success)?.let {
                                viewModel.showAllMedia(
                                    title,
                                    it.data
                                )
                            }
                        }
                    )
                }

                item {
                    val title = stringResource(R.string.section_trending_weekly_tvs)
                    MediaSection(
                        title = title,
                        section = sections.trendingWeeklyTvs,
                        onItemClick = onItemClick,
                        onRetry = viewModel::loadTrendingWeeklyTvs,
                        onSeeAllClick = {
                            (sections.trendingWeeklyTvs as? SectionUiState.Success)?.let {
                                viewModel.showAllMedia(
                                    title,
                                    it.data
                                )
                            }
                        }
                    )
                }

                item {
                    val title = stringResource(R.string.section_now_playing)
                    MediaSection(
                        title = title,
                        section = sections.nowPlayingMovies,
                        onItemClick = onItemClick,
                        onRetry = viewModel::loadNowPlaying,
                        onSeeAllClick = {
                            (sections.nowPlayingMovies as? SectionUiState.Success)?.let {
                                viewModel.showAllMedia(
                                    title,
                                    it.data
                                )
                            }
                        }
                    )
                }

                item {
                    val title = stringResource(R.string.section_upcoming)
                    MediaSection(
                        title = title,
                        section = sections.upcomingMovies,
                        onItemClick = onItemClick,
                        onRetry = viewModel::loadUpcoming,
                        onSeeAllClick = {
                            (sections.upcomingMovies as? SectionUiState.Success)?.let {
                                viewModel.showAllMedia(
                                    title,
                                    it.data
                                )
                            }
                        }
                    )
                }
            }
        }

        is FawMainUiState.MediaList -> {
            val mediaListState = uiState as FawMainUiState.MediaList
            Column {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        items = mediaListState.items,
                        key = { it.mediaId }
                    ) { item ->
                        MediaCard(
                            item = item,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = onItemClick
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun MediaSection(
    title: String,
    section: SectionUiState<MediaDto>,
    onItemClick: (Long, MediaType) -> Unit,
    onRetry: () -> Unit,
    onSeeAllClick: () -> Unit,
    maxItems: Int = 8
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        if (section is SectionUiState.Success) {
            SectionHeader(title, onSeeAllClick = onSeeAllClick)
        } else {
            SectionHeader(title)
        }

        when (section) {
            SectionUiState.Loading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
            }

            is SectionUiState.Error -> {
                ErrorContent(
                    message = stringResource(section.messageId),
                    modifier = Modifier.fillMaxWidth(),
                    onRetry = onRetry
                )
            }

            is SectionUiState.Success -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = section.data.take(maxItems),
                        key = { it.mediaId }
                    ) { item ->
                        MediaCard(
                            item = item,
                            modifier = Modifier.width(140.dp),
                            onClick = onItemClick
                        )
                    }
                }
            }
        }
    }
}

