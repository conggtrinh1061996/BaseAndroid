package com.androidtech.domain.use_case

import com.androidtech.domain.model.news.Article
import com.androidtech.domain.repository.AuthRepository
import com.androidtech.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(uid: String): Flow<Result<List<Article>>> {
        return newsRepository.getAllBookmarks(uid)
    }
}