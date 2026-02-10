package com.androidtech.ui.fragment.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.androidtech.domain.model.news.Article
import com.androidtech.domain.use_case.GetNewsListUseCase
import com.androidtech.domain.use_case.GetSavedBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsListUseCase: GetNewsListUseCase,

): ViewModel() {

    /*data class NewsSate (
        val listNews: Flow<PagingData<Article>>? = null,
        val errorMessages: String = "News app"
    ): UIState
    override fun createInitialState(): NewsSate {
        return NewsSate()
    }

    fun fetchNews() {
        setState {
            copy(listNews = getNewsListUseCase().cachedIn(viewModelScope))
        }
        Logger.d("Called fetchNews() in viewmodel with ${uiState.value.listNews}")
    }*/

    private val selectedCategory = MutableStateFlow("general")
    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val newsPagingFlow: Flow<PagingData<Article>> = combine(
        selectedCategory,
        searchQuery
    ) { category, query ->
        Pair(category, query)
    }.flatMapLatest { (category, query) ->
        getNewsListUseCase(category, query)
    }.cachedIn(viewModelScope)

    fun selectCategory(category: String) {
        selectedCategory.value = category
    }
    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }
}