package com.mm.fawshows_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mm.domain.model.MediaDto
import com.mm.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FawSearchViewModel @Inject constructor(private val repository: MediaRepository): ViewModel() {

    private val _uiState = MutableStateFlow<FawSearchUiState>(FawSearchUiState.Idle)
    val uiState: StateFlow<FawSearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            _uiState.value = FawSearchUiState.Idle
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)
            _uiState.value = FawSearchUiState.Loading
            try {
                val result = repository.searchMedia(query)
                _uiState.value = FawSearchUiState.Success(result)
            } catch (e: Exception) {
                print(e.message)
                _uiState.value = FawSearchUiState.Error(com.mm.ui.R.string.network_error)
            }
        }
    }
}
sealed class FawSearchUiState {
    object Idle : FawSearchUiState()
    object Loading : FawSearchUiState()
    data class Success(val results: List<MediaDto>) : FawSearchUiState()
    data class Error(val messageId: Int) : FawSearchUiState()
}
