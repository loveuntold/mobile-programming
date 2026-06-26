package com.example.news_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.data.model.Article
import com.example.news_app.data.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchState = MutableStateFlow<NewsUiState>(NewsUiState.Success(emptyList()))
    val searchState: StateFlow<NewsUiState> = _searchState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            try {
                _uiState.value = NewsUiState.Loading
                val response = repository.getNews()
                _uiState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        if (query.isBlank()) {
            _searchState.value = NewsUiState.Success(emptyList())
            return
        }

        searchJob = viewModelScope.launch {
            delay(400)
            try {
                _searchState.value = NewsUiState.Loading
                val response = repository.searchNews(query)
                _searchState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _searchState.value = NewsUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
