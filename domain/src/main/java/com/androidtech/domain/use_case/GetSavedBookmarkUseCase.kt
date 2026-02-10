package com.androidtech.domain.use_case

import com.androidtech.domain.repository.NewsRepository
import javax.inject.Inject

class GetSavedBookmarkUseCase @Inject constructor(
    private val newsRepository: NewsRepository
){
    operator fun invoke() = newsRepository.getSavedBookmarks()
}