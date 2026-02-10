package com.androidtech.domain.use_case

import com.androidtech.domain.repository.NewsRepository
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(uid: String, newsId: String): Result<Unit> {
        return newsRepository.removeBookmark(uid, newsId)

    }
}