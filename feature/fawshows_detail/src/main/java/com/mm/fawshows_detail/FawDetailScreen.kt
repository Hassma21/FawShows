package com.mm.fawshows_detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mm.domain.model.Cast
import com.mm.feature.detail.R
import com.mm.ui.componenets.ErrorContent
import com.mm.ui.componenets.LoadingContent
import com.mm.ui.state.FabState
import com.mm.ui.state.TopAppBarState

@SuppressLint("LocalContextResourcesRead")
@Composable
fun FawDetailScreen(
    viewModel: FawDetailViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass,
    onBackPressed: () -> Unit,
    onFabChange: (FabState) -> Unit,
    onAppBarChange: (TopAppBarState) -> Unit
) {
    val isTablet = windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        onAppBarChange(
            TopAppBarState(
                visible = true,
                title = "",
                showBack = true,
                onBackClick = onBackPressed
            )
        )
        onFabChange(
            FabState.Shown(
                iconRes = R.drawable.ic_add_favourite,
                contentDescription = "Add to favourites",
                onClick = { viewModel.addToFavourite() }
            )
        )
    }
    DisposableEffect(Unit) {
        onDispose { onFabChange(FabState.Hidden) }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { resId ->
            if (resId != 0) {
                Toast.makeText(
                    context,
                    context.resources.getString(resId),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showPlayer by remember { mutableStateOf(false) }
    when (uiState) {
        is FawDetailUiState.Loading -> LoadingContent()

        is FawDetailUiState.Error -> {
            ErrorContent(
                message = stringResource((uiState as FawDetailUiState.Error).messageId),
                modifier = Modifier.fillMaxSize(),
                onRetry = viewModel::onRetry
            )
        }

        is FawDetailUiState.Media -> {
            val media = (uiState as FawDetailUiState.Media).mediaDetail
            val padding = if (isTablet) 24.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = media.mediaPosterPath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(if (isTablet) 16f / 9f else 2f / 3f),
                    contentScale = ContentScale.Crop
                )

                Column(Modifier.padding(padding)) {
                    Text(
                        text = media.mediaTitle,
                        style = if (isTablet)
                            MaterialTheme.typography.headlineSmall
                        else
                            MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(12.dp))

                    media.mediaTrailer?.let { trailer ->
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { showPlayer = true }
                        ) {
                            Text(stringResource(R.string.watch_trailer))
                        }
                        if (showPlayer) {
                            YouTubePlayerWithFallback(
                                youtubeKey = media.mediaTrailer!!.youtubeKey,
                                lifecycleOwner = LocalLifecycleOwner.current,
                                onFallbackToYoutube = {
                                    showPlayer = false
                                }
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                    }


                    Text(
                        text = stringResource(R.string.overview),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = media.mediaOverview
                            ?: stringResource(R.string.overview_empty),
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = stringResource(R.string.cast_and_crew),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(12.dp))

                    if (isTablet) {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(120.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(media.mediaCast) { cast ->
                                CastItem(cast)
                            }
                        }
                    } else {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(media.mediaCast) { cast ->
                                CastItem(cast)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CastItem(cast: Cast) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = cast.profileUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = cast.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

