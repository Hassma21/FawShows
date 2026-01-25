package com.mm.fawshows_favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mm.common.R
import com.mm.common.logger.CrashLogger
import com.mm.domain.model.Favourite
import com.mm.domain.model.MediaDto
import com.mm.domain.model.MediaType
import com.mm.domain.use_case.GetFavouritesUseCase
import com.mm.domain.use_case.RemoveFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FawFavouritesViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase,
    private val crashLogger: CrashLogger
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavouritesUiState>(FavouritesUiState.Loading)
    val uiState: StateFlow<FavouritesUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<Int>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeFavourites()
    }

    fun observeFavourites() {
        getFavouritesUseCase()
            .onEach { favourites ->
                if (favourites.isEmpty()) {
                    _uiState.value = FavouritesUiState.Empty
                } else {
                    val mediaList = favourites.map { it.toMediaDto() }
                    _uiState.value = FavouritesUiState.Success(mediaList)
                }
            }
            .catch { e ->
                crashLogger.recordException(e)
                _uiState.value = FavouritesUiState.Error(com.mm.ui.R.string.error_occurred)
            }
            .launchIn(viewModelScope)
    }

    fun deleteFavourite(tmdbId: Long) {
        viewModelScope.launch {
            try {
                removeFavouriteUseCase(tmdbId)
                _uiEvent.send(com.mm.ui.R.string.removed_from_favourites)
            } catch (e: Exception) {
                crashLogger.recordException(e)
                _uiEvent.send(com.mm.ui.R.string.error_occurred)
            }
        }
    }
}
sealed interface FavouritesUiState {
    object Loading : FavouritesUiState
    object Empty : FavouritesUiState
    data class Success(val items: List<MediaDto>) : FavouritesUiState
    data class Error(val messageId: Int) : FavouritesUiState
}

fun Favourite.toMediaDto() = MediaDto(
    mediaId = tmdbId,
    mediaTitle = title,
    mediaPosterPath = posterPath,
    mediaVotAvarage = rating,
    mediaType = MediaType.entries[mediaType]
)