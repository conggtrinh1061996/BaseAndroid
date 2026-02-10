package com.androidtech.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.androidtech.data.local.database.AppDatabase
import com.androidtech.data.model.news.ArticleEntity
import com.androidtech.data.model.news.RemoteKey
import com.androidtech.data.model.news.toEntity
import com.androidtech.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator (
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val apiKey: String,
    private val category: String,
    private val q: String
): RemoteMediator<Int, ArticleEntity>() {

    private val articleDao = database.getNewsDao()
    private val removeKeysDao = database.remoteKeyDao()
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success( true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(true)

                removeKeysDao.remoteKeyById(lastItem.id)?.nextKey
                    ?: return MediatorResult.Success(true)
            }
        }
        try {
            val response = apiService.getTopHeadlines(
                category = category,
                q = q,
                page = page,
                pageSize = state.config.pageSize,
                apiKey = apiKey
            )

            if(!response.isSuccessful) {
                return MediatorResult.Error(HttpException(response))
            }
            val body = response.body()
                ?:return MediatorResult.Error(
                    NullPointerException("Response body is null")
                )

            val articles = body.articles
            val endOfPagination = articles.isEmpty()

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    removeKeysDao.clearRemoteKeys()
                    articleDao.clearArticles(category)
                }
                val entities = articles.map { it.toEntity(category, q) }
                val keys = entities.map {
                    RemoteKey(
                        articleId = it.id,
                        prevKey = if(page == 1) null else page -1,
                        nextKey = if(endOfPagination) null else page +1
                    )
                }
                removeKeysDao.insertAll(keys)
                articleDao.insertArticles(entities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}