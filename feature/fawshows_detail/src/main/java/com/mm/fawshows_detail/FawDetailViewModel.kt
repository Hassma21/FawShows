package com.mm.fawshows_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mm.common.logger.CrashLogger
import com.mm.domain.model.MediaDetail
import com.mm.domain.use_case.GetMediaDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mm.common.result.Result
import com.mm.domain.model.Favourite
import com.mm.domain.use_case.AddFavouriteUseCase
import com.mm.fawshows_detail.FawDetailUiState.*
import com.mm.ui.R
import com.mm.navigation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FawDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMediaDetailUseCase: GetMediaDetailUseCase,
    private val addFavouriteUseCase: AddFavouriteUseCase,
    private val crashLogger: CrashLogger
) : ViewModel() {

    private val _uiState = MutableStateFlow<FawDetailUiState>(Loading)
    val uiState: StateFlow<FawDetailUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<Int>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val args = savedStateHandle.toRoute<Route.ScreenDetail>()

    init {
        loadMediaDetail()
    }

    private fun loadMediaDetail() {
        viewModelScope.launch {
            getMediaDetailUseCase(args.tmdbId, args.mediaType)
                .collect { result ->
                    _uiState.value = when (result) {
                        is Result.Loading -> Loading
                        is Result.Success -> Media(result.data)
                        is Result.Error -> Error(
                            R.string.network_error
                        )
                    }
                }
        }
    }

    fun onRetry() {
        loadMediaDetail()
    }

    fun addToFavourite() {
        val currentState = uiState.value
        if (currentState !is Media) return

        viewModelScope.launch {
            try {
                addFavouriteUseCase(
                    Favourite(
                        tmdbId = args.tmdbId,
                        title = currentState.mediaDetail.mediaTitle,
                        posterPath = currentState.mediaDetail.mediaPosterPath ?: "",
                        rating = currentState.mediaDetail.mediaVoteAverage ?: 0.0,
                        mediaType = args.mediaType
                    )
                )
                _uiEvent.send(R.string.added_to_favourites)
            } catch (e: Exception) {
                crashLogger.recordException(e)
                _uiEvent.send(R.string.error_occurred)
            }
        }
    }
}


sealed interface FawDetailUiState {

    data object Loading : FawDetailUiState

    data class Media(
        val mediaDetail: MediaDetail,
    ) : FawDetailUiState

    data class Error(val messageId: Int) : FawDetailUiState

}