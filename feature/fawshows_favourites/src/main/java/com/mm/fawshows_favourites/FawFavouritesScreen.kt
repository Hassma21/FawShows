package com.mm.fawshows_favourites

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mm.domain.model.MediaType
import com.mm.feature.favourites.R
import com.mm.ui.componenets.ErrorContent
import com.mm.ui.componenets.LoadingContent
import com.mm.ui.componenets.MediaCard
import com.mm.ui.state.TopAppBarState

@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FawFavouritesScreen(
    viewModel: FawFavouritesViewModel = hiltViewModel(),
    onItemClick: (Long, MediaType) -> Unit,
    onBackPressed: () -> Unit,
    onAppBarChange: (TopAppBarState) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val topBarTitle = stringResource(R.string.my_favourites)
    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { resId ->
            if (resId != 0) {
                Toast.makeText(context, context.resources.getString(resId), Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {

        onAppBarChange(
            TopAppBarState(
                visible = true,
                title = topBarTitle,
                showBack = true,
                onBackClick = onBackPressed
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is FavouritesUiState.Loading -> LoadingContent()
            is FavouritesUiState.Empty -> EmptyStateContent(stringResource(R.string.not_found_media_at_favourite))
            is FavouritesUiState.Error -> ErrorContent(
                message = stringResource(state.messageId),
                modifier = Modifier.fillMaxSize(),
                onRetry = { viewModel.observeFavourites() }
            )
            is FavouritesUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.items,
                        key = { it.mediaId }
                    ) { media ->
                        MediaCard(
                            item = media,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            onClick = onItemClick,
                            onDeleteClick = { viewModel.deleteFavourite(media.mediaId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateContent(contentMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = contentMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    }
}
