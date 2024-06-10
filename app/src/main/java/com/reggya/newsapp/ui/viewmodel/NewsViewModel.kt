package com.reggya.newsapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.reggya.newsapp.core.model.ArticlesItem
import com.reggya.newsapp.core.network.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    private var _news = MutableStateFlow<PagingData<ArticlesItem>>(PagingData.empty())
    val news : MutableStateFlow<PagingData<ArticlesItem>> get() = _news

    init {
        getHeadlineNews()
    }

    private fun getHeadlineNews() {
        viewModelScope.launch {
            newsRepository.getHeadlineNews()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _news.value = it
                }
        }
    }

}