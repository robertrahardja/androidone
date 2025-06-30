package com.rr.adone.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rr.adone.domain.model.ContentItem
import com.rr.adone.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    
    fun searchContent(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            contentRepository.searchContent(query)
                .catch { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Search failed"
                    )
                }
                .collect { results ->
                    _uiState.value = _uiState.value.copy(
                        searchResults = results,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
    
    fun clearSearch() {
        _uiState.value = SearchUiState()
    }
}

data class SearchUiState(
    val searchResults: List<ContentItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)