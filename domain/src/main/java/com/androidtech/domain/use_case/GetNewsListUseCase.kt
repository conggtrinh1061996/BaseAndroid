package com.androidtech.domain.use_case

import androidx.paging.PagingData
import com.androidtech.domain.model.news.Article
import com.androidtech.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
) {

    operator fun invoke(category: String, q: String): Flow<PagingData<Article>> {
        return newsRepository.getNewsPaging(category, q)
    }
}