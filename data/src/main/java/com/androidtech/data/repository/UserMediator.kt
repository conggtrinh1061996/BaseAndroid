package com.androidtech.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.androidtech.data.local.database.AppDao
import com.androidtech.data.network.ApiService
import com.androidtech.domain.model.user.User
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserMediator @Inject constructor(
    private val apiService: ApiService,
    private val appDao: AppDao
): RemoteMediator<Int, User>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    (lastItem.id!! / state.config.pageSize) + 1
                }
            }
            val response = apiService.getUsersByPage("https://api.github.com/users", page, state.config.pageSize)
            val users = response.body() ?: listOf()
            if (loadType == LoadType.REFRESH) appDao.clearAll()
            appDao.insertAll(users)
            MediatorResult.Success(endOfPaginationReached = users.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}