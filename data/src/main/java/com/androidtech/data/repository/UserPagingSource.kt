package com.androidtech.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.androidtech.data.model.user.transform
import com.androidtech.data.network.ApiService
import com.androidtech.domain.model.user.User
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val apiService: ApiService
): PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getUsersByPage(
                url = "https://api.github.com/users?per_page=20&since=$currentPage",
                10, 1
            )
            if (response.isSuccessful && response.body() != null) {
                LoadResult.Page(
                    data = response.body()?.map { it.transform() } ?: listOf(),
                    prevKey = if (currentPage == 1) null else (currentPage - 1),
                    nextKey = if (response.body()?.isEmpty() == true) null else currentPage + 1
                )
            } else  {
                LoadResult.Error(Exception("error"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}