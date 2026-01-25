package com.mm.fawshows_search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mm.domain.model.MediaType
import com.mm.ui.componenets.MediaCard
import com.mm.ui.state.TopAppBarState
import com.mm.feature.search.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FawSearchScreen(
    viewModel: FawSearchViewModel = hiltViewModel(),
    onAppBarChange: (TopAppBarState) -> Unit,
    onBackPressed: () -> Unit,
    onItemClick: (Long, MediaType) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var query by remember { mutableStateOf("") }
    val topBarTitle = stringResource(R.string.search_title)
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.search(it)
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.search(query) })
        )

        Spacer(modifier = Modifier.height(12.dp))

        when (uiState) {
            is FawSearchUiState.Idle -> {
                Text(stringResource(R.string.search_hint), style = MaterialTheme.typography.bodyMedium)
            }

            is FawSearchUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is FawSearchUiState.Error -> {
                Text(
                    text = stringResource((uiState as FawSearchUiState.Error).messageId),
                    color = MaterialTheme.colorScheme.error
                )
            }

            is FawSearchUiState.Success -> {
                val results = (uiState as FawSearchUiState.Success).results
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(results, key = { it.mediaId }) { media ->
                        MediaCard(
                            item = media,
                            onClick = onItemClick,
                        )
                    }
                }
            }
        }
    }
}

