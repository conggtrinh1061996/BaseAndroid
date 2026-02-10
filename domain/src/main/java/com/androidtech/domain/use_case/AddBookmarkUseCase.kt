package com.androidtech.domain.use_case

import com.androidtech.domain.model.news.Article
import com.androidtech.domain.repository.AuthRepository
import com.androidtech.domain.repository.NewsRepository
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(uid: String, article: Article) : Result<Unit> {
       return newsRepository.addBookmark(uid, article)
    }
}